/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;
import modelo.Usuario;
import vista.VistaPrincipal;

/**
 *
 * @author karl
 */
public final class CtrlEditarMiUsuario implements ActionListener{
    CtrlVistaLogin login;
    Usuario usuario;
    Ctrl_registrar registrar;
    VistaPrincipal principal;
    String tipo;
   // public CtrlEditar
    public CtrlEditarMiUsuario(Usuario usuario,VistaPrincipal principal,String tipo,Ctrl_registrar registrar){
    this.usuario=usuario;
   this.principal=principal;
   this.registrar=registrar;
   this.tipo=tipo;
    cargarDatos();
    principal.btnCancelarEdit.addActionListener(this);
    principal.btnEditarEdit1.addActionListener(this);
   principal.btnGuardarEdit.addActionListener(this);
   
}

    public void activarBotones(boolean estado){
        
    }
    public void activarTextos(boolean estado){
        principal.txtContraseniaEdit.setEnabled(estado); 
        principal.txtApellidoEdit.setEnabled(estado);
         principal.txtNombreEdit.setEnabled(estado);
    }
    public void cargarDatos(){
      principal.txtNombreEdit.setText(usuario.getNombre());
       // principal.temporal.setText("casa");
       principal.txtApellidoEdit.setText(usuario.getApellido());
        principal.txtDependenciaEdit.setText(usuario.getDependencia());
        principal.txtContraseniaEdit.setText(usuario.getContrasenia());
        principal.txtTipoEdit.setText(tipo);
    }
public void Guardar(){
    usuario.setNombre( principal.txtNombreEdit.getText());
    usuario.setApellido(principal.txtApellidoEdit.getText());
    usuario.setContrasenia( principal.txtContraseniaEdit.getText());
    ActualizarArchivo();
}
public void ActualizarArchivo(){///para cambiar el registro en los archivo de ese usuario
    JOptionPane.showMessageDialog(null, "CAMBIOS REALIZADOS CON EXISTO");
    registrar.ActualizarParametros(usuario,  usuario.getCorreo());
}
 public boolean comprobarCampos(){
        if(principal.txtNombreEdit.getText().length()==0 || principal.txtApellidoEdit.getText().length()==0||principal.txtContraseniaEdit.getText().length()==0)
        {//ismepty india si un string esta vacio devuele true y si esta llano devuelve false
           //JOptionPane.showMessageDialog(null, "Llene todos los campos");
         
           return false;
       }
        return true;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
       if(e.getSource()== principal.btnGuardarEdit){
           if(!(comprobarCampos())){
               JOptionPane.showMessageDialog(null, "INGRESE TODO LOS CAMPOS");
           
           }else{
              Guardar(); 
               principal.btnGuardarEdit.setEnabled(false);
       principal.btnEditarEdit1.setEnabled(true);
       activarTextos(false);
           }
    
      
       }
       if(e.getSource()== principal.btnEditarEdit1){
           principal.btnEditarEdit1.setEnabled(false);
       activarTextos(true);
      }
        if(e.getSource()== principal.btnCancelarEdit){
             principal.btnEditarEdit1.setEnabled(true);
             principal.btnGuardarEdit.setEnabled(false);
       activarTextos(false);
       }
    }

   
}
