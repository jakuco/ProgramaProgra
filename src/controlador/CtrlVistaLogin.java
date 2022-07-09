/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import modelo.Administrador;
import modelo.Autorizador;
import modelo.Solicitante;
import modelo.Usuario;
import vista.VistaLogin;
import vista.VistaPrincipal;

/**
 *
 * @author davqi
 */
public class CtrlVistaLogin implements ActionListener{
    
    private VistaLogin ventanaLogin;
    private Usuario usuario; 
    private Map<String,Usuario> usuarios;//Lista de usuarios 
    
    public CtrlVistaLogin(){
        ventanaLogin = new VistaLogin();
        usuario = new Usuario();
        usuarios = new HashMap<String,Usuario>();
        cargarUsuarios();
        ventanaLogin.ingresarJbtn.addActionListener(this);
        ventanaLogin.setVisible(true);
    }
    
    public void cargarUsuarios(){//Carga el conjunto de usuarios 
        File archUsuarios = new File("usuarios");
        try{
            FileInputStream fis = new FileInputStream(archUsuarios);
            ObjectInputStream ois = new ObjectInputStream(fis);
            usuarios = (Map<String, Usuario>) ois.readObject();
            ois.close();
        }
        catch(FileNotFoundException e){
            JOptionPane.showMessageDialog(null, "No se ha encontrado el archivo usuarios", "Error", 2);
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(null, "Error al leer el archivo usuarios", "Error", 2);
        }
        catch(ClassNotFoundException e){
            JOptionPane.showMessageDialog(null, "Los archivos no son compatibles", "Error", 2);
        }
    }
    
    public void iniciar(){//Abre una ventana de acuerdo al tipo de usuario
        VistaPrincipal ventanaUsuario = new VistaPrincipal();
        if(usuario instanceof Administrador){
            CtrlGestionarEdificios ctlGestEdif = new CtrlGestionarEdificios(ventanaUsuario);
            return;
        }
        if(usuario instanceof Autorizador){
            ventanaUsuario.general.remove(ventanaUsuario.administrador);
            ventanaUsuario.setVisible(true);
            return;
        }
        if(usuario instanceof Solicitante){
            ventanaUsuario.general.remove(ventanaUsuario.administrador);
            ventanaUsuario.general.remove(ventanaUsuario.solicitudes);
            Ctrl_MisSolicitudes ctrlSolicitudes = new Ctrl_MisSolicitudes(ventanaUsuario);
            ventanaUsuario.setVisible(true);
            return;
        }
        if(usuario instanceof Usuario){
            ventanaUsuario.general.remove(ventanaUsuario.administrador);
            ventanaUsuario.general.remove(ventanaUsuario.solicitudes);
            ventanaUsuario.general.remove(ventanaUsuario.misSolicitudes);
            
            ventanaUsuario.setVisible(true);
            return;
        }    
        
    }
    
    public void ingresar(){//Revisa si el usuario existe y obtiene sus datos
        if(usuarios.containsKey(ventanaLogin.correoTxt.getText())){
            usuario=usuarios.get(ventanaLogin.correoTxt.getText());
            if(usuario.getContrasenia().equals(ventanaLogin.contraselaJP.getText())){
                ventanaLogin.setVisible(false);
                iniciar();
            }else{
                JOptionPane.showMessageDialog(null, "La contraseña es incorrecta", "Error", 2);
            }
            
        }else{
            JOptionPane.showMessageDialog(null, "El usuario ingresado no existe", "Error", 2);
        }
    }
    
        
    public void cargarDatosArchivos(String nombreArchivo, Object datos){//Carga el conjunto de usuarios 
        File arch = new File(nombreArchivo);
        try{
            FileInputStream fis = new FileInputStream(arch);
            ObjectInputStream ois = new ObjectInputStream(fis);
            datos = ois.readObject();
            ois.close();
        }
        catch(FileNotFoundException e){
            JOptionPane.showMessageDialog(null, "No se ha encontrado el archivo: " + nombreArchivo, "Error", 2);
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(null, "Error al leer el archivo: "+ nombreArchivo, "Error", 2);
        }
        catch(ClassNotFoundException e){
            JOptionPane.showMessageDialog(null, "Los archivos no son compatibles", "Error", 2);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==ventanaLogin.ingresarJbtn) ingresar();
    }

}
