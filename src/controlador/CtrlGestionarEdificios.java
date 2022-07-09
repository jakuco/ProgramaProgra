/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Edificio;
import modelo.Espacio;
import vista.VistaPrincipal;

/**
 *
 * @author davqi
 */
public class CtrlGestionarEdificios implements ActionListener, ItemListener{
    private VistaPrincipal vista;
    private ArrayList<Edificio> edificios;
    private boolean cambios;
    
    public CtrlGestionarEdificios(VistaPrincipal ventana){
        vista = ventana;
        cambios = false;
        cargarEdificios();
        vista.setVisible(true);
        vista.BtnAgregar.addActionListener(this);
        vista.BtnEditar.addActionListener(this);
        vista.BtnEliminar.addActionListener(this);
        vista.BtnGuardar.addActionListener(this);
        vista.edificios1.addItemListener(this);
        vista.salas.addItemListener(this);
        controlarComponentes();
    }
    
    public void controlarComponentes(){
        if(vista.edificios1.getSelectedItem().toString().equals("Nuevo Edificio")){//Si esta seleccionado nuevo edificio
            vista.salas.setEnabled(false);
            vista.BtnEliminar.setEnabled(false);
            vista.BtnEditar.setEnabled(false);
            vista.tipoTxt.setEditable(false);
            vista.capacidadTxt.setEditable(false);
            vista.descripcionTxt.setEditable(false);
            vista.numPisos.setEditable(true);
            vista.BtnAgregar.setEnabled(true);
        }else{//Si esta seleccionado un edificio
            vista.salas.setEnabled(true);
            vista.BtnEliminar.setEnabled(true);
            vista.BtnEditar.setEnabled(true);
            vista.BtnAgregar.setEnabled(false);
            if(vista.salas.getSelectedItem().toString().equals("Nueva Sala")){//Si esta seleccionado nueva sala
                vista.BtnAgregar.setEnabled(true);
                vista.BtnEliminar.setEnabled(true);
                vista.BtnEditar.setEnabled(true);
                vista.numPisos.setEditable(false);
                vista.tipoTxt.setEditable(true);
                vista.capacidadTxt.setEditable(true);
            }
            else{//Si está seleccionada una sala
                vista.BtnEliminar.setEnabled(true);
                vista.BtnEditar.setEnabled(true);
                vista.numPisos.setEditable(false);
                vista.BtnAgregar.setEnabled(false);
                vista.tipoTxt.setEditable(true);
                vista.capacidadTxt.setEditable(true);
            }
        }
        if(cambios){
            vista.BtnGuardar.setEnabled(true);
        }
        else{
            vista.BtnGuardar.setEnabled(false);
        }
    }
    
    public void limpiarCampos(){
        vista.capacidadTxt.setText("");
        vista.descripcionTxt.setText("");
        vista.tipoTxt.setText("");
        vista.nombreTxt.setText("");
        vista.numPisos.setText("");
        vista.ubicacionTxt.setText("");
        vista.idTxt.setText("");
    }
    
    public void cargarEdificios(){
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
        cargarEdificiosCB();
    }
    
    public void cargarSalasCB(){
        int count = 1;
        while(!(vista.edificios1.getItemCount()==1)){
            vista.salas.removeItemAt(count);
        }
        if(!(edificios.get(vista.edificios1.getSelectedIndex()-1).getListaEspacios().isEmpty())){
            for(Espacio e: edificios.get(vista.edificios1.getSelectedIndex()-1).getListaEspacios()){
                vista.salas.addItem(e.getCodigo());
            }
        }
        else return;
    }
    
    public void cargarEdificiosCB(){
        int count=1;
        while(!(vista.edificios1.getItemCount()==1)){
            vista.edificios1.removeItemAt(count);
        }
        for(Edificio e:edificios){
            vista.edificios1.addItem(e.getNombre());
        }
    }
    
    public void agregarEdificio(){
        Edificio nuevo = new Edificio();
        nuevo.setCodigo(vista.idTxt.getText());
        nuevo.setNombre(vista.nombreTxt.getText());
        nuevo.setNumPlantas(Integer.parseInt(vista.numPisos.getText()));
        nuevo.setNumEspacios(0);//ESTO HAY QUE REVISAR
        nuevo.setUbicacion(vista.ubicacionTxt.getText());
        edificios.add(nuevo);
        JOptionPane.showMessageDialog(null, "Edificio agregado", "Exito", 1);
        cambios = true;
        controlarComponentes();
        limpiarCampos();
        vista.edificios1.addItem(nuevo.getNombre());
    }
    
    public void agregarSala(){
        Espacio nuevo = new Espacio();
        nuevo.setCapacidad(Integer.parseInt(vista.capacidadTxt.getText()));
        nuevo.setCodigo(vista.idTxt.getText());
        nuevo.setDescripcion(vista.descripcionTxt.getText());
        nuevo.setTipo(Integer.parseInt(vista.tipoTxt.getText()));
        nuevo.setUbicacion(vista.ubicacionTxt.getText());
        edificios.get(vista.edificios1.getSelectedIndex()-1).addEspacio(nuevo);
        JOptionPane.showMessageDialog(null, "Sala agregada", "Exito", 1);
        cambios = true;
        controlarComponentes();
        cargarSalasCB();
        limpiarCampos();
    }
    
    public void eliminar(){
        if(vista.salas.getSelectedItem().toString().equals("Nueva Sala")){//Elimina un edificio
            edificios.remove(vista.edificios1.getSelectedIndex()-1);
            cargarEdificiosCB();
            JOptionPane.showMessageDialog(null, "Edificio eliminado", "Exito", 1);
        }
        else{//Elimina una sala de un edificio
             edificios.get(vista.edificios1.getSelectedIndex()-1).removeEspacio(vista.salas.getSelectedIndex()-1);
             vista.salas.remove(vista.salas.getSelectedIndex());
             JOptionPane.showMessageDialog(null, "Sala eliminada", "Exito", 1);
        }
        cambios = true;
        controlarComponentes();
    }
    
    public void editar(){
        
    }
    
    public void guardar(){
        File archivoEdificios = new File("edificios");
        try{
            FileOutputStream fos = new FileOutputStream(archivoEdificios);
            ObjectOutputStream ous = new ObjectOutputStream(fos);
            ous.writeObject(edificios);
            ous.close();
            JOptionPane.showMessageDialog(null, "Modificaciones guardadas", "Exito", 1);
        }
        catch(FileNotFoundException e){
            JOptionPane.showMessageDialog(null, "No existe el archivo edificios", "Error", 2);
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(null, "Error al guardar el archivo edificios", "Error", 2);
        }
        controlarComponentes();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==vista.BtnAgregar&&vista.edificios1.getSelectedIndex()==0) agregarEdificio();
        else if(e.getSource()==vista.BtnAgregar&&vista.salas.getSelectedIndex()==0) agregarSala();
        if(e.getSource()==vista.BtnEliminar) eliminar();
        if(e.getSource()==vista.BtnEditar) editar();
        if(e.getSource()==vista.BtnGuardar) guardar();
    }


    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getSource()==vista.edificios1){
            if(!(vista.edificios1.getSelectedIndex()==0)){
                cargarSalasCB();
            }
            controlarComponentes();
        }
        if(e.getSource()==vista.salas){
            controlarComponentes();
        }
    }
    
}
