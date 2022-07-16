/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.util.ArrayList;
import vista.VistaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Edificio;
import modelo.Espacio;
import modelo.LineaAgenda;
import modelo.Solicitante;
import modelo.Solicitud;
import modelo.Usuario;

/**
 *
 * @author Jorge
 */
public class Ctrl_MisSolicitudes implements ActionListener,ItemListener{
    
    private VistaPrincipal vista;
    private ArrayList<Edificio> edificios;
    private Map<String,Usuario> usuarios;//Lista de usuarios, la clave es el correo
    Solicitante solicitante;
    Solicitud solicitud;
    Solicitante usuario;
    LineaAgenda linea;
    
    public Ctrl_MisSolicitudes(VistaPrincipal vista,Solicitante usuario, Map<String,Usuario> usuarios) {
        this.vista = vista;
        this.usuario=usuario;
        this.usuarios = usuarios;
        this.vista.cancelarSolicitudBtn.addActionListener(this);
        this.vista.solicitarBtn.addActionListener(this);
        this.vista.boxEdificio.addItemListener(this);
        this.vista.boxSala.addItemListener(this);
        solicitante =new Solicitante();
        linea=new  LineaAgenda();
        solicitud=new Solicitud();
        cargarEdificios();
        cargarEdificiosCB();
        cargarEspaciosCB();
        cargarMisSolicitudes();
        cargarDescripcion();
    }
    
    public void cargarEdificios(){//Carga el archivo de edificios
        edificios = new ArrayList<Edificio>();
        File archivo = new File("edificios");
        try{
            FileInputStream fis = new FileInputStream(archivo);
            ObjectInputStream ois = new ObjectInputStream(fis);
            edificios = (ArrayList<Edificio>) ois.readObject();
            ois.close();
        }
        catch(FileNotFoundException e){
            JOptionPane.showMessageDialog(null, "No se ha encontrado el archivo edificios, se creara uno nuevo", "Error", 2);
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(null, "Error al cargar el archivo edificios", "Error", 2);
        }
        catch(ClassNotFoundException e){
            JOptionPane.showMessageDialog(null, "Archivo edificios no compatible", "Error", 2);
        }
    }
       
    
    public void cargarMisSolicitudes(){//Carga las solicitudes realizadas
        ArrayList<Solicitud> misSolicitudes = usuario.getSolicitudes();
        vista.SolicitudesUsuario_jTable.removeAll();
        String[][] data = {};
        String[] col = {"Codigo","Estado","Sala","Fecha","Hora Inicial","Hora Final","Actividad","Responsable"};
        DefaultTableModel df = new DefaultTableModel(data,col);
        vista.SolicitudesUsuario_jTable.setModel(df);
        for(Solicitud s:misSolicitudes){
            String[] linea = new String[8];
            linea[0]=s.getCodigo();
            linea[1]=s.getEstado();
            linea[2]=s.getSala();
            linea[3]=s.getFecha();
            linea[4]=s.getLinea().getHoraInicial();
            linea[5]=s.getLinea().getHoraFinal();
            linea[6]=s.getLinea().getActividad();
            linea[7]=s.getLinea().getResponsable();
            df.addRow(linea);
        }
    }
    
    public void cargarEdificiosCB(){
        for(Edificio e:edificios){
            vista.boxEdificio.addItem(e.getNombre());
        }
        cargarDescripcion();
    }
    
    public void cargarEspaciosCB(){
        vista.boxSala.removeAllItems();
        for(Espacio e:edificios.get(vista.boxEdificio.getSelectedIndex()).getListaEspacios()){
            vista.boxSala.addItem(e.getNombre());
        }
    }
    
    public void cargarDescripcion(){
        
        Edificio e = edificios.get(vista.boxEdificio.getSelectedIndex());
        if(vista.boxSala.getSelectedIndex()!=-1){
            Espacio s = e.getListaEspacios().get(vista.boxSala.getSelectedIndex());
            vista.descripcion.setText(s.getDescripcion());
        }      
    }
    
    public void guardar(){//Guarda los cambios en el usuario
        File archUsrs = new File("usuarios");
        try{
            FileOutputStream fos = new FileOutputStream(archUsrs);
            ObjectOutputStream ous = new ObjectOutputStream(fos);
            ous.writeObject(usuarios);
            ous.close();
            JOptionPane.showMessageDialog(null, "Solicitud enviada", "Exito", 1);
        }
        catch(FileNotFoundException e){
            JOptionPane.showMessageDialog(null, "El archivo usuarios no existe", "Error", 2);
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(null, "Error al guardar el archivo", "Error", 2);
        }
    }
    
    public void guardarSolicitud(Solicitud s){
        Edificio e = edificios.get(vista.boxEdificio.getSelectedIndex());
        Espacio es = e.getListaEspacios().get(vista.boxSala.getSelectedIndex());
        es.enqueSolicitud(s);
        File archEdfs = new File("edificios");
        try{
            FileOutputStream fos = new FileOutputStream(archEdfs);
            ObjectOutputStream ous = new ObjectOutputStream(fos);
            ous.writeObject(edificios);
            ous.close();
        }
        catch(FileNotFoundException f){
            JOptionPane.showMessageDialog(null, "No se ha encontrado el archivo edificios", "Error", 2);
        }
        catch(IOException i){
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error al guardar la solicitud", "Error", 2);
        }
    }
    
     public void solicitar(){
         Solicitud nueva = new Solicitud();
         nueva.setCodigo(""+usuario.getSolicitudes().size()+1);//el codigo es el numero de solicitud
         nueva.setEstado("En espera");
         nueva.setSala(vista.boxSala.getSelectedItem().toString());
         nueva.setFecha(vista.fechaSolTxt.getText());
         LineaAgenda nuevaLinea = new LineaAgenda();
         nuevaLinea.setActividad(vista.txtActividad.getText());
         nuevaLinea.setHoraFinal(vista.txtHoraFinal.getText());
         nuevaLinea.setHoraInicial(vista.txtHoraInicial.getText());
         nuevaLinea.setResponsable(usuario.getNombre());
         nueva.setLinea(nuevaLinea);
         usuario.anadirmapa(""+usuario.getSolicitudes().size()+1, nueva);
         guardar();//Guarda la solicitud en el usuario
         guardarSolicitud(nueva);//Guarda la solicitud en la sala
     }
    
     public void guardarCambiosEdificio(){
         File archEdfs = new File("edificios");
         try{
             FileOutputStream fos = new FileOutputStream(archEdfs);
             ObjectOutputStream ous = new ObjectOutputStream(fos);
             ous.writeObject(edificios);
             ous.close();
         }
         catch(IOException e){
             JOptionPane.showMessageDialog(null, "Error al guardar cambios en el archivo edificios", "Error", 2);
         }
     }
     
    public void cancelarSolicitud(){
        Solicitud s = usuario.getSolicitud(vista.SolicitudesUsuario_jTable.getValueAt(vista.SolicitudesUsuario_jTable.getSelectedRow(), 0).toString());
        usuario.removerSolicitud(s.getCodigo());
        //Se podria agregar un metodo que lo elimine de la agenda si ha sido aceptado
        for(Edificio e:edificios){//Busca a que espacio se envió la solicitud y la remueve de la cola de solicitudes
            for(Espacio es:e.getListaEspacios()){
                for(Solicitud so:es.getSolicitudes())
                    if(so.getCodigo().equals(s.getCodigo())){
                        es.getSolicitudes().remove(so);
                    }
            }
        }
        guardar();
        guardarCambiosEdificio();
        cargarMisSolicitudes();
    }
    


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==vista.solicitarBtn) solicitar();
        if(e.getSource()==vista.cancelarSolicitudBtn) cancelarSolicitud();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getSource()==vista.boxEdificio) cargarEspaciosCB();
        if(e.getSource()==vista.boxSala) cargarDescripcion();
    }
}
