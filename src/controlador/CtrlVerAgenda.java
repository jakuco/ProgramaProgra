/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Agenda;
import modelo.Edificio;
import modelo.Espacio;
import modelo.LineaAgenda;
import vista.VistaPrincipal;

/**
 *
 * @author davqi
 */
public class CtrlVerAgenda implements ActionListener, ItemListener{
    private VistaPrincipal ventanaUsuario;
    private ArrayList<Edificio> edificios;

    public CtrlVerAgenda(VistaPrincipal ventanaUsuario) {
        this.ventanaUsuario = ventanaUsuario;
        edificios = new ArrayList<Edificio>();
        cargarEdificios();
        cargarComboEdificios();
        cargarComboSalas();
        this.ventanaUsuario.verAgendaBtn.addActionListener(this);
        this.ventanaUsuario.edificios.addItemListener(this);
    }
    
    public void cargarEdificios(){
        File archEdificios = new File("edificios");
        try{
            FileInputStream fis = new FileInputStream(archEdificios);
            ObjectInputStream ois = new ObjectInputStream(fis);
            edificios = (ArrayList<Edificio>) ois.readObject();
            ois.close();
        }
        catch(FileNotFoundException e){
            JOptionPane.showMessageDialog(null, "No se ha encontrado el archivo edificios", "Error", 2);
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(null, "Error al leer el archivo edificios", "Error", 2);
        }
        catch(ClassNotFoundException e){
            JOptionPane.showMessageDialog(null, "Los archivos no son compatibles", "Error", 2);
        }
    }
    
    public void cargarComboEdificios(){
        for(Edificio e:edificios){
            ventanaUsuario.edificios.addItem(e.getNombre());
        }
    }
    
    public void cargarComboSalas(){
        ventanaUsuario.espacios.removeAllItems();
        for(Espacio e:edificios.get(ventanaUsuario.edificios.getSelectedIndex()).getListaEspacios()){
            ventanaUsuario.espacios.addItem(e.getNombre());
        }
    }
    
    public DefaultTableModel iniciarTabla(){
        ventanaUsuario.horario.removeAll();
        String[][] data = {};
        String[] col = {"Hora Inicial", "Hora Final", "Actividad", "Responsable"};
        DefaultTableModel df = new DefaultTableModel(data,col);
        return df;
    }
    
    public void verAgenda(){
        DefaultTableModel df = iniciarTabla();
        ventanaUsuario.horario.setModel(df);
        Espacio e = edificios.get(ventanaUsuario.edificios.getSelectedIndex()).obtenerEspacio(ventanaUsuario.espacios.getSelectedIndex());
        Agenda ag = e.getAgenda(ventanaUsuario.fechaTxt.getText());
        LineaAgenda[] lineas = ag.getAgenda();//Toma las lineas de la agenda
        for(int i=0;i<lineas.length;i++){
            if(lineas[i]!=null){
                String[] nuevaLinea = new String[4];
                nuevaLinea[0] = lineas[i].getHoraInicial();
                nuevaLinea[1] = lineas[i].getHoraFinal();
                nuevaLinea[2] = lineas[i].getActividad();
                nuevaLinea[3] = lineas[i].getResponsable();
                df.addRow(nuevaLinea);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==ventanaUsuario.verAgendaBtn) verAgenda();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        cargarComboSalas();
    }

}
