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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Edificio;
import modelo.Espacio;
import vista.VistaPrincipal;
import modelo.Servicio;
import modelo.Material;
import modelo.Elemento;

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
        vista.elementos.addItemListener(this);
        controlarComponentes();
    }
    
    public void controlarComponentes(){
        if(vista.edificios1.getSelectedItem().toString().equals("Nuevo Edificio")){//Si esta seleccionado nuevo edificio
            vista.jRadioButton_Material.setEnabled(false);
            vista.jRadioButton_Servicio.setEnabled(false);
            vista.salas.setEnabled(false);
            vista.elementos.setEnabled(false);
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
                vista.elementos.setEnabled(false);
                vista.BtnAgregar.setEnabled(true);
                vista.BtnEliminar.setEnabled(true);
                vista.BtnEditar.setEnabled(true);
                vista.numPisos.setEditable(false);
                vista.tipoTxt.setEditable(true);
                vista.capacidadTxt.setEditable(true);
            } else {//Si está seleccionada una sala
                vista.elementos.setEnabled(true);
                vista.BtnEliminar.setEnabled(true);
                vista.BtnEditar.setEnabled(true);
                vista.numPisos.setEditable(false);
                vista.BtnAgregar.setEnabled(false);
                vista.tipoTxt.setEditable(true);
                vista.capacidadTxt.setEditable(true);
                vista.descripcionTxt.setEditable(true);
                if (vista.elementos.getSelectedItem().toString().equals("Nuevo elemento")){
                    vista.jRadioButton_Material.setEnabled(true);
                    vista.jRadioButton_Servicio.setEnabled(true);
                    vista.BtnAgregar.setEnabled(true);
                    vista.BtnEliminar.setEnabled(true);
                    vista.BtnEditar.setEnabled(true);
                    vista.numPisos.setEditable(false);
                    vista.tipoTxt.setEditable(true);
                    vista.capacidadTxt.setEditable(true);
                }else{
                    vista.jRadioButton_Material.setEnabled(false);
                    vista.jRadioButton_Servicio.setEnabled(false);
                    vista.BtnEliminar.setEnabled(true);
                    vista.BtnEditar.setEnabled(true);
                    vista.numPisos.setEditable(false);
                    vista.BtnAgregar.setEnabled(false);
                    vista.tipoTxt.setEditable(true);
                    vista.capacidadTxt.setEditable(true);
                    vista.descripcionTxt.setEditable(true);
                }
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
        edificios = new ArrayList<>();
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
    
    public void cargarElementosCB(){
        int count = 1;
        while(!(vista.elementos.getItemCount()==1)){
            vista.elementos.removeItemAt(count);
        }
        if(!(edificios.get(vista.edificios1.getSelectedIndex()-1).getListaEspacios().isEmpty())){
           
            try{
               if(!(edificios.get(vista.edificios1.getSelectedIndex()-1).obtenerEspacio(vista.salas.getSelectedIndex()-1).obtenerListaElementos().isEmpty())){
                    System.out.println("Indice del Espacio: "+ vista.salas.getSelectedIndex());
                    for (Elemento obtenerListaElemento : edificios.get(vista.edificios1.getSelectedIndex()-1).obtenerEspacio(vista.salas.getSelectedIndex()-1).obtenerListaElementos()) {
                        vista.elementos.addItem(obtenerListaElemento.getDescripcion());
                    }
                }
            } catch (NullPointerException e){
                System.out.println("Error en punteros: cargar elementos");
            }
        }
        
    }
    
    public void cargarSalasCB(){
        int count = 1;
        while(!(vista.salas.getItemCount()==1)){
            vista.salas.removeItemAt(count);
        }
        if(!(edificios.get(vista.edificios1.getSelectedIndex()-1).getListaEspacios().isEmpty())){
            for(Espacio e: edificios.get(vista.edificios1.getSelectedIndex()-1).getListaEspacios()){
                vista.salas.addItem(e.getCodigo());
            }
        }
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
        
        try{
            nuevo.setCapacidad(Integer.parseInt(vista.capacidadTxt.getText()));
            nuevo.setCodigo(vista.idTxt.getText());
            nuevo.setDescripcion(vista.descripcionTxt.getText());
            nuevo.setTipo(Integer.parseInt(vista.tipoTxt.getText()));
            nuevo.setUbicacion(vista.ubicacionTxt.getText());
            nuevo.setNombre(vista.nombreTxt.getText());
            edificios.get(vista.edificios1.getSelectedIndex()-1).addEspacio(nuevo);
            JOptionPane.showMessageDialog(null, "Sala agregada", "Exito", 1);
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Error al ingresar número");
        }
        cambios = true;
        controlarComponentes();
        cargarSalasCB();
        limpiarCampos();
    } 
    
    public void agregarServicio(){
        
        try{
            Servicio nuevo = new Servicio(vista.nombreTxt.getText(), Integer.parseInt(vista.capacidadTxt.getText()));
            System.out.println("Elemento agregado: "+nuevo.getDescripcion()+",en agregar servicio.");
            edificios.get(vista.edificios1.getSelectedIndex()-1).obtenerEspacio(vista.salas.getSelectedIndex()-1).addElemento(nuevo);
            JOptionPane.showMessageDialog(null, "Servicio agregado", "Exito", 1);
        } catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Error al ingresar número");
        } catch (NullPointerException e){
            JOptionPane.showMessageDialog(null, "Error de punteros");
        }
        
        cambios=true;
        controlarComponentes();
        cargarElementosCB();
        limpiarCampos();
    }
    
    public void agregarMaterial(){
        try{
            Material nuevo = new Material(vista.nombreTxt.getText(), Integer.parseInt(vista.capacidadTxt.getText()));
            System.out.println("Elemento agregado: "+nuevo.getDescripcion()+",en agregar servicio.");
            edificios.get(vista.edificios1.getSelectedIndex()-1).obtenerEspacio(vista.salas.getSelectedIndex()-1).addElemento(nuevo);
            JOptionPane.showMessageDialog(null, "Material agregado", "Exito", 1);
        } catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Error al ingresar número");
        } catch (NullPointerException e){
            JOptionPane.showMessageDialog(null, "Error de punteros");
        }
        
        cambios=true;
        controlarComponentes();
        cargarElementosCB();
        limpiarCampos();
    }
    
    public void agregarElemento(){
        if (vista.jRadioButton_Servicio.isSelected())
        agregarServicio();
        else if (vista.jRadioButton_Material.isSelected())
            agregarMaterial();
        
    }
    
    public void eliminar(){
        if(vista.salas.getSelectedItem().toString().equals("Nueva Sala")){//Elimina un edificio
            edificios.remove(vista.edificios1.getSelectedIndex()-1);
            cargarEdificiosCB();
            JOptionPane.showMessageDialog(null, "Edificio eliminado", "Exito", 1);
        }
        else{//Elimina una sala de un edificio
            
            if (vista.elementos.getSelectedItem().toString().equals("Nuevo Elemento")){     
                edificios.get(vista.edificios1.getSelectedIndex()-1).removeEspacio(vista.salas.getSelectedIndex()-1);
                vista.salas.remove(vista.salas.getSelectedIndex());
                controlarComponentes();
                cargarSalasCB();
                JOptionPane.showMessageDialog(null, "Sala eliminada", "Exito", 1);
            } else {
                //Busca los elementos para borrarlos
                edificios.get(vista.edificios1.getSelectedIndex()-1).obtenerEspacio(vista.salas.getSelectedIndex()-1).obtenerListaElementos().remove(vista.elementos.getSelectedIndex()-1);
                vista.elementos.remove(vista.elementos.getSelectedIndex());
                controlarComponentes();
                cargarElementosCB();
                JOptionPane.showMessageDialog(null, "Elemento eliminado", "Exito",1);  
            } 
        }
        cambios = true;
        controlarComponentes();
    }
    
    public void editar(){
        
        System.out.println("Se entró en editar");
        
        if(vista.salas.getSelectedItem().toString().equals("Nueva Sala")){//Edita un edificio
            
            editarEdificio();
            cargarEdificiosCB();
        }
        else{//Edita una sala
            
            if (vista.elementos.getSelectedItem().toString().equals("Nuevo elemento")){     
                System.out.println("Se ha editado una sala");
                editarEspacio();
                cargarSalasCB();
            } else {
               
                editarElemento();
                cargarElementosCB();
            } 
            
        }
        cambios = true;
        controlarComponentes();
    }
    
    public void editarEdificio(){
        
        edificios.get(vista.edificios1.getSelectedIndex()-1);
        edificios.get(vista.edificios1.getSelectedIndex()-1).setCodigo(vista.idTxt.getText());
        edificios.get(vista.edificios1.getSelectedIndex()-1).setNombre(vista.nombreTxt.getText());
        edificios.get(vista.edificios1.getSelectedIndex()-1).setNumPlantas(Integer.parseInt(vista.numPisos.getText()));
        edificios.get(vista.edificios1.getSelectedIndex()-1).setNumEspacios(0);//ESTO HAY QUE REVISAR
        edificios.get(vista.edificios1.getSelectedIndex()-1).setUbicacion(vista.ubicacionTxt.getText());
        
        JOptionPane.showMessageDialog(null, "Edificio editado", "Exito", 1);
        cambios = true;
        controlarComponentes();
        limpiarCampos();
        vista.edificios1.addItem(edificios.get(vista.edificios1.getSelectedIndex()-1).getNombre());
    }
    
    public void editarElemento(){
        edificios.get(vista.edificios1.getSelectedIndex()-1).obtenerEspacio(vista.salas.getSelectedIndex()-1).obtenerListaElementos().get(vista.elementos.getSelectedIndex()-1).setDescripcion(vista.nombreTxt.getText());
        edificios.get(vista.edificios1.getSelectedIndex()-1).obtenerEspacio(vista.salas.getSelectedIndex()-1).obtenerListaElementos().get(vista.elementos.getSelectedIndex()-1).setCantidad(Integer.parseInt(vista.capacidadTxt.getText()));
        JOptionPane.showMessageDialog(null, "Elemento editado", "Exito", 1);
        cambios = true;
        controlarComponentes();
        limpiarCampos();
        vista.edificios1.addItem(edificios.get(vista.edificios1.getSelectedIndex()-1).getNombre());
    }
    
    public void editarEspacio(){
        try{
            
            edificios.get(vista.edificios1.getSelectedIndex()-1).obtenerEspacio(vista.salas.getSelectedIndex()-1).setCapacidad(Integer.parseInt(vista.capacidadTxt.getText()));
            edificios.get(vista.edificios1.getSelectedIndex()-1).obtenerEspacio(vista.salas.getSelectedIndex()-1).setCodigo(vista.idTxt.getText());
            edificios.get(vista.edificios1.getSelectedIndex()-1).obtenerEspacio(vista.salas.getSelectedIndex()-1).setDescripcion(vista.descripcionTxt.getText());
            edificios.get(vista.edificios1.getSelectedIndex()-1).obtenerEspacio(vista.salas.getSelectedIndex()-1).setTipo(Integer.parseInt(vista.tipoTxt.getText()));
            edificios.get(vista.edificios1.getSelectedIndex()-1).obtenerEspacio(vista.salas.getSelectedIndex()-1).setUbicacion(vista.ubicacionTxt.getText());
            edificios.get(vista.edificios1.getSelectedIndex()-1).obtenerEspacio(vista.salas.getSelectedIndex()-1).setNombre(vista.nombreTxt.getText());
            
            JOptionPane.showMessageDialog(null, "Espacios Editado", "Exito", 1);
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Error al ingresar número");
        }
        cambios = true;
        controlarComponentes();
        cargarSalasCB();
        limpiarCampos();
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
        else if (e.getSource()==vista.BtnAgregar&&vista.elementos.getSelectedIndex()==0) agregarElemento();
        else if (e.getSource()==vista.BtnAgregar&&vista.elementos.getSelectedIndex()!=0)
            JOptionPane.showMessageDialog(null, "Selecione:|Nuevo elemento| para agregar un nuevo elemento.");

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
            if (!(vista.salas.getSelectedIndex()==0)){
                cargarElementosCB();
            }
            controlarComponentes();
        } 
        if (e.getSource()==vista.elementos){
            controlarComponentes();
        }
    }
    
}
