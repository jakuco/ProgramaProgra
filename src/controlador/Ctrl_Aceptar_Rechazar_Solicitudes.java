/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Queue;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Agenda;
import modelo.Edificio;
import modelo.Espacio;
import modelo.Solicitante;
import modelo.Solicitud;
import modelo.Usuario;

/**
 *
 * @author Jorge
 */
public class Ctrl_Aceptar_Rechazar_Solicitudes implements ActionListener,ItemListener{
    
    private VistaPrincipal vista;
    private ArrayList<Edificio> edificios;
    private Queue<Solicitud> solicitudes;
    private Map<String,Usuario> usuarios;

    public Ctrl_Aceptar_Rechazar_Solicitudes(VistaPrincipal vista, Map<String,Usuario> usuarios) {
        this.vista = vista;
        this.usuarios = usuarios;
        vista.aceptarBtn.addActionListener(this);
        vista.rechazarBtn.addActionListener(this);
        vista.edificiosAR.addItemListener(this);
        vista.salasAR.addItemListener(this);
        cargarEdificios();
        cargarEdificiosCB();
        cargarEspaciosCB();
        cargarSolicitudes();
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
    
    public void cargarEdificiosCB(){
        for(Edificio e:edificios){
            vista.edificiosAR.addItem(e.getNombre());
        }
    }
    
    public void cargarEspaciosCB(){
        vista.salasAR.removeAllItems();
        for(Espacio e:edificios.get(vista.edificiosAR.getSelectedIndex()).getListaEspacios()){
            vista.salasAR.addItem(e.getNombre());
        }
    }
    
    public void cargarSolicitudes(){
        Edificio e = edificios.get(vista.edificiosAR.getSelectedIndex());
        if(vista.salasAR.getSelectedIndex()!=-1){
            Espacio es = e.getListaEspacios().get(vista.salasAR.getSelectedIndex());
            vista.tablaSolicitudes.removeAll();
            String[][] data = {};
            String[] colum = {"Código","Estado","Espacio","Fecha","Hora Inicial","Hora Final","Actividad","Responsable"};
            DefaultTableModel df = new DefaultTableModel(data,colum);
            vista.tablaSolicitudes.setModel(df);
            solicitudes = es.getSolicitudes();
//            System.out.println(solicitudes.peek());
//            if(!solicitudes.isEmpty()){
            /*
                for(Solicitud s:solicitudes){
                    String[] lineaSolicitud = new String[8];
                    lineaSolicitud[0] = s.getCodigo();
                    lineaSolicitud[1] = s.getEstado();
                    lineaSolicitud[2] = s.getSala();
                    lineaSolicitud[3] = s.getFecha();
                    lineaSolicitud[4] = s.getLinea().getHoraInicial();
                    lineaSolicitud[5] = s.getLinea().getHoraFinal();
                    lineaSolicitud[6] = s.getLinea().getActividad();
                    lineaSolicitud[7] = s.getLinea().getResponsable();
                    df.addRow(lineaSolicitud);
                }
            */
        }
    }
    
    public void guardarCambiosEdificios(){
        File archEdfs = new File("edificios");
        try{
            FileOutputStream fos = new FileOutputStream(archEdfs);
            ObjectOutputStream ous = new ObjectOutputStream(fos);
            ous.writeObject(edificios);
            ous.close();
            JOptionPane.showMessageDialog(null, "Solicitud procesada", "Exito", 1);
        }
        catch(FileNotFoundException e){
            JOptionPane.showMessageDialog(null, "No se ha encontrado el archivo edificios", "Error", 2);
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(null, "Error al guardar los cambios", "Error", 2);
        }
    }
    
    public void guardarCambiosUsuarios(){
        File usrs = new File("usuarios");
        try{
            FileOutputStream fos = new FileOutputStream(usrs);
            ObjectOutputStream ous = new ObjectOutputStream(fos);
            ous.writeObject(usuarios);
            ous.close();
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(null, "Error al guardar el archivo usuarios", "Error", 2);
        }
    }
    
    public void modificarSolicitudUsuario(Solicitud s, String estado){//modifica el estado de la solicitud del usuario
        for(Usuario u:usuarios.values()){
            if(u instanceof Solicitante){
                for(Solicitud so:((Solicitante) u).getSolicitudes()){
                    if(so.getCodigo().equals(s.getCodigo())){//busca al dueño de la solicitud en base al codigo
                        so.setEstado(estado);
                        guardarCambiosUsuarios();
                    }
                }
            }
        }
    }
    
    public void aceptarSolicitud(){
        Edificio e = edificios.get(vista.edificiosAR.getSelectedIndex());
        if(vista.salasAR.getSelectedIndex()!=-1){
            Espacio es = e.getListaEspacios().get(vista.salasAR.getSelectedIndex());
            Solicitud s = solicitudes.peek();
            Agenda a = es.getAgenda(vista.tablaSolicitudes.getValueAt(0, 3).toString());//toma la agenda del espacio de esa fecha
            if(a == null){//Si la agenda está vacia para ese día
                a = new Agenda();
                a.setFecha(s.getFecha());
                a.addLineaAgenda(s.getLinea());
                es.setAgenda(s.getFecha(), a);
                solicitudes.remove(s);
                guardarCambiosEdificios();
                modificarSolicitudUsuario(s,"Aprobado");
                cargarSolicitudes();
            }else{
                for(int i=0;i<16;i++){
                    if(a.getAgenda()[i]!=null){
                        if(a.getAgenda()[i].getHoraInicial().equals(s.getLinea().getHoraInicial())){//Si la hora ya esta ocupada
                            JOptionPane.showMessageDialog(null, "El espacio ya está ocupado para esa hora", "Error", 2);
                            return;
                        }
                    }
                }
                a.addLineaAgenda(s.getLinea());//Si la hora esta libre
                solicitudes.remove(s);
                guardarCambiosEdificios();
                modificarSolicitudUsuario(s,"Aprobado");
                cargarSolicitudes();
            }
        }
    }
    
    public void rechazarSolicitud(){
        Solicitud s = solicitudes.peek();
        solicitudes.remove(s);
        guardarCambiosEdificios();
        modificarSolicitudUsuario(s,"Rechazado");
        cargarSolicitudes();
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==vista.aceptarBtn) aceptarSolicitud();
        if (e.getSource()==vista.rechazarBtn) rechazarSolicitud();
    }
    
    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getSource()==vista.edificiosAR) cargarEspaciosCB();
        if(e.getSource()==vista.salasAR) cargarSolicitudes();
    }
}
