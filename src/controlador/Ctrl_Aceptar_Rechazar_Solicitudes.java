/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import vista.VistaPrincipal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jorge
 */
public class Ctrl_Aceptar_Rechazar_Solicitudes implements ActionListener{
    
    private VistaPrincipal vista;
    private DefaultTableModel modeloTabla;

    public Ctrl_Aceptar_Rechazar_Solicitudes(VistaPrincipal vista) {
        this.vista = vista;
        
        vista.jButton_Aceptar_Solicitud.addActionListener(this);
        vista.jButton_Rechazar_Solicitud.addActionListener(this);
        
        iniciarTabla_SolicitarEnAgenda();
        mostrarSolicitudes();
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==vista.jButton_Aceptar_Solicitud){
            int fila=vista.jTable_Aceptar_Rechazar_Solicitudes.getSelectedRow();
            JOptionPane.showMessageDialog(null, "Solicitud " + (fila+1)+" aceptada.", "Error", 2);

            modeloTabla.removeRow(fila);
        }
        
        if (e.getSource()==vista.jButton_Rechazar_Solicitud){
            int fila=vista.jTable_Aceptar_Rechazar_Solicitudes.getSelectedRow();
            JOptionPane.showMessageDialog(null, "Solicitud " + (fila+1)+" rechazada.", "Error", 2);

            modeloTabla.removeRow(fila);
        }
    }
    
    
    public void iniciarTabla_SolicitarEnAgenda(){
        vista.jTable_Aceptar_Rechazar_Solicitudes.removeAll();
        String data[][]={};
        String col[]={"ID Sala","Edificio","Cargo Solicitante","Correo Solicitante","Fecha","Hora"}; // se pide el nombre del jugador una sola vez al inicio
        modeloTabla = new DefaultTableModel(data, col);
        vista.jTable_Aceptar_Rechazar_Solicitudes.setModel(modeloTabla);
    }
    
    public void mostrarSolicitudes(String Edificio, String Sala){
            for (int i=0; i<3; i++){
                modeloTabla.insertRow(i, new Object []{});
               
                modeloTabla.setValueAt("1:"+ i, i, 0); 
   
                modeloTabla.setValueAt("2: "+ i, i, 1); 
                modeloTabla.setValueAt("3: "+i, i, 2);
               
                modeloTabla.setValueAt("4: "+i, i, 3);
                
                modeloTabla.setValueAt("5: "+i, i, 4);
                
                modeloTabla.setValueAt("6: "+i, i, 4);
            }
        
    }
    
    public void mostrarSolicitudes(){
            for (int i=0; i<3; i++){
                modeloTabla.insertRow(i, new Object []{});
               
                modeloTabla.setValueAt("1:"+ i, i, 0); 
   
                modeloTabla.setValueAt("2: "+ i, i, 1); 
                modeloTabla.setValueAt("3: "+i, i, 2);
               
                modeloTabla.setValueAt("4: "+i, i, 3);
                
                modeloTabla.setValueAt("5: "+i, i, 4);
                
                modeloTabla.setValueAt("6: "+i, i, 4);
            }
        
    }
}
