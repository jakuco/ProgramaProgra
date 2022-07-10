/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.Color;
import java.util.ArrayList;
import vista.VistaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Usuario;
import modelo.Solicitud_Borrar;

/**
 *
 * @author Jorge
 */
public class Ctrl_MisSolicitudes implements ActionListener{
    
    private VistaPrincipal vista;
    private DefaultTableModel modeloTabla;
    private ArrayList<Solicitud_Borrar> solicitudes;
    private DefaultTableModel modeloTab;
    public Ctrl_MisSolicitudes(VistaPrincipal vista) {
        this.vista = vista;
        JOptionPane.showMessageDialog(null, "Constructor mis solicitudes", "Error", 2);
        this.vista.cancelarSolicitud_jButton.addActionListener(this);
        this.vista.jButton_Solicitar.addActionListener(this);
       // vista.
        //this.vista.SolicitudesUsuario_jTable.setModel(modeloTabla);
        //elementosEnTabla(/*Usuario*/);
        iniciarTabla_Mis_Solicitudes();
        mostrarSolicitudes();
        iniciarTabla_SolicitarEnAgenda();

        mostrarSolicitudesAgenda();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==vista.cancelarSolicitud_jButton){
            int fila=vista.SolicitudesUsuario_jTable.getSelectedRow();
            EliminarSolicitud(/*se pasan los parámetros para eliminar la solicitud del sistema*/);
            //Usuario.listaSolicitudes(filas); // se elimina la solicitud de las lista del Usuario
            elementosEnTabla(/*Usuario*/);
            //System.out.println("Se realiza una acción");
            JOptionPane.showMessageDialog(null, "Solicitud eliminada: " + (fila+1), "Error", 2);

            modeloTab.removeRow(fila);

        }
        if (e.getSource()== vista.jButton_Solicitar){
            int fila= vista.jTable_HorarioSolicitar.getSelectedRow();
            //guardarSolicitud(obtenerSolicitud(filas));
            //se obtiene la solicitud de la tabla
            //vista.jTable_HorarioSolicitar.setBackground(Color.red);
            //vista.jTable_HorarioSolicitar.getCellEditor(fila, fila).
            modeloTabla.removeRow(fila);
        }
    }
    
    public void iniciarTabla_SolicitarEnAgenda(){
        vista.jTable_HorarioSolicitar.removeAll();
        String data[][]={};
        String col[]={"Hora","Lunes","Martes","Miércoles","Jueves","Viernes","Estado"}; // se pide el nombre del jugador una sola vez al inicio
        modeloTabla = new DefaultTableModel(data, col);
        vista.jTable_HorarioSolicitar.setModel(modeloTabla);
    }
    
    public void iniciarTabla_Mis_Solicitudes(){
        vista.SolicitudesUsuario_jTable.removeAll();
        String data[][]={};
        String col[]={"Parte 1","Parte 2"}; // se pide el nombre del jugador una sola vez al inicio
        modeloTab = new DefaultTableModel(data, col);
        vista.SolicitudesUsuario_jTable.setModel(modeloTab);
    }
    
    public void obtenerSolicitud(){
        
    }
    public void EliminarSolicitud(){
        
    }
    
    public void guardarSolicitud(){
        
    }
    
    public void elementosEnTabla(/*Usuario*/){ // aquí pasa toda la acción de la ventana
        //vista.mostrarLanzadas.setText(valueOf(modelo.getLanzadas()));// se muestra las veces que se ha lanzado
     /*   
     /*   for (int i=0; i<Usuario.cantidadSolicitudes;i++)
        modeloTabla.insertRow(modeloTabla.getRowCount(), new Object []{});
        
        for (int i=0; i<modeloTabla.getRowCount();i++){
            
            //System.out.println("---");
        /*modeloTabla.setValueAt(Usuario.getHora, i, 0); 
   
        modeloTabla.setValueAt(Usuario.getFecha.getApellido(), i, 1);
        
        modeloTabla.setValueAt(modelo.Espacios.getEdad(), i,2);
        
        modeloTabla.setValueAt(modelo.Tiempo.cantEmpleados(),i, 3);
        }   
        */
    }
    
     public void mostrarSolicitudesAgenda(){
            for (int i=0; i<3; i++){
                modeloTabla.insertRow(i, new Object []{});
        
                modeloTabla.setValueAt("1: "+ i, i, 0); 
   
                modeloTabla.setValueAt("2: "+ i, i, 1); 
                
                modeloTabla.setValueAt("3: "+i, i, 2);
               
                modeloTabla.setValueAt("4: "+i, i, 3);
                
                modeloTabla.setValueAt("5: "+i, i, 4);
                
                modeloTabla.setValueAt("6: "+i, i, 4);
                
                modeloTabla.setValueAt("7: "+i, i, 4);
            }
        
    }
    
    
    public void mostrarSolicitudes(){
            for (int i=0; i<3; i++){
                modeloTab.insertRow(i, new Object []{});
               
                modeloTab.setValueAt("XD: "+ i, i, 0); 
   
                modeloTab.setValueAt("Sol: "+ i, i, 1); 
            }
        
    }
    public void cargarSolicitudes(){//Carga el conjunto de usuarios 
        File archUsuarios = new File("solicitudes");
        try{
            FileInputStream fis = new FileInputStream(archUsuarios);
            ObjectInputStream ois = new ObjectInputStream(fis);
            solicitudes = (ArrayList<Solicitud_Borrar>) ois.readObject();
            ois.close();
        }
        catch(FileNotFoundException e){
            JOptionPane.showMessageDialog(null, "No se ha encontrado el archivo de solicitudes", "Error", 2);
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(null, "Error al leer el archivo de solicitudes", "Error", 2);
        }
        catch(ClassNotFoundException e){
            JOptionPane.showMessageDialog(null, "Los archivos no son compatibles", "Error", 2);
        }
    }
}
