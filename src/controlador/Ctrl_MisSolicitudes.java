/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import vistasproyfinal.VistaPricipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jorge
 */
public class Ctrl_MisSolicitudes implements ActionListener{
    
    private VistaPricipal vista;
    private DefaultTableModel modeloTabla;
    public Ctrl_MisSolicitudes() {
        vista = new VistaPricipal();
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
}
