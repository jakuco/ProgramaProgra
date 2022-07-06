/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

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
    public Ctrl_MisSolicitudes() {
        vista = new VistaPrincipal();
        vista.cancelarSolicitud_jButton.addActionListener(this);
        vista.SolicitudesUsuario_jTable.setModel(modeloTabla);
        elementosEnTabla(/*Usuario*/);
        
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==vista.cancelarSolicitud_jButton){
            int filas=vista.SolicitudesUsuario_jTable.getSelectedRow();
            EliminarSolicitud(/*se pasan los parámetros para eliminar la solicitud del sistema*/);
            //Usuario.listaSolicitudes(filas); // se elimina la solicitud de las lista del Usuario
            elementosEnTabla(/*Usuario*/);
        }
        if (e.getSource()== vista.jButton_Solicitar){
            int filas= vista.jTable_HorarioSolicitar.getSelectedRow();
            //guardarSolicitud(obtenerSolicitud(filas));
            //se obtiene la solicitud de la tabla
        }
    }
    public void obtenerSolicitud(){
        
    }
    public void EliminarSolicitud(){
        
    }
    
    public void guardarSolicitud(){
        
    }
    
    public void elementosEnTabla(/*Usuario*/){ // aquí pasa toda la acción de la ventana
        //vista.mostrarLanzadas.setText(valueOf(modelo.getLanzadas()));// se muestra las veces que se ha lanzado
        
        for (int i=0; i</*Usuario.cantidadSolicitudes*/ 3;i++)
        modeloTabla.insertRow(modeloTabla.getRowCount(), new Object []{});
        
        for (int i=0; i<modeloTabla.getRowCount();i++){
            
            //System.out.println("---");
        /*modeloTabla.setValueAt(Usuario.getHora, i, 0); 
   
        modeloTabla.setValueAt(Usuario.getFecha.getApellido(), i, 1);
        
        modeloTabla.setValueAt(modelo.Espacios.getEdad(), i,2);
        
        modeloTabla.setValueAt(modelo.Tiempo.cantEmpleados(),i, 3);*/
        }    
    }
    
    public void cargarUsuarios(){//Carga el conjunto de usuarios 
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
