
package controlador;

/**
 *
 * @author karl
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import modelo.Administrador;
import modelo.Autorizador;
import modelo.Solicitante;
import modelo.Usuario;
import vista.VistaPrincipal;
//import vista.VistaGestionUsuarios;
public class Ctrl_registrar implements ActionListener,KeyListener,Serializable{
    //VistaGestionUsuarios view;
    private VistaPrincipal view;
    private Map<String,Usuario> usuarios;//el mapa donde debe cargarse los usuarios al unir los contrladores debe estar en el login
    Usuario usuario;
    Solicitante solicitante;
    Autorizador Administrador;
    Solicitante autorizador;
    int bandera;
  public Ctrl_registrar(VistaPrincipal vista){
      this.view=vista;
      //view.setVisible(true);
        usuarios=new HashMap<String,Usuario>();//inicia el mapa
        usuario=new Usuario();
        solicitante=new Solicitante();
        Administrador=new Administrador();
        autorizador=new Autorizador();
         cargarUsuarios();
        view.txtcorreo.addKeyListener(this);
        view.txtcedula.addKeyListener(new EventoNumeros());
        view.txttelefono.addKeyListener(new EventoNumeros());
        view.btnGuardar.addActionListener(this);
        view.btnNuevo.addActionListener(this);
        view.btnEditar.addActionListener(this);
        view.btnCancelar.addActionListener(this);
        view.btnEliminar.addActionListener(this);
        view.boxTipo.addActionListener(this);
      ActivarBotones(false);
      ActivarTextos(false);
      
      ///PARTE PARA EL CARGADO Y GUARDAR EL ARCHIVO esta pate deberia llamarce desde el controlador 
  }
    public void cargarUsuarios(){//Carga el conjunto de usuarios 
        File archUsuarios = new File("usuarios");
        try{
            FileInputStream fis = new FileInputStream(archUsuarios);
            ObjectInputStream ois = new ObjectInputStream(fis);
            usuarios = (Map<String,Usuario>) ois.readObject();
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
    
  public void GrabarDatosArchivo() {
    File users = new File("usuarios");
        try{
            FileOutputStream fos = new FileOutputStream(users);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(usuarios); 
        }catch(IOException e){    
        }
}
  public void ActualizarArchivo(){///para manterer los datos guardados y actualizados en los archivos en los cambios realizados 
      GrabarDatosArchivo();
      cargarUsuarios();
  }
  
              //fin parte cargar
                      ////////PARTE PARA GUARDAR EN EL MAPA
  public void grabar(){//para tomar los datos de todos los campos 
      int prioridad;
      if(bandera==1){//si es nuevo
          if(usuarios.containsKey(view.txtcorreo.getText())){
                JOptionPane.showMessageDialog(null, "EL USUARIO YA EXISTE");
          }else{
              if(view.boxTipo.getSelectedItem()=="SOLICITANTE"){
         Usuario p;//temporal para usar el contructor
         prioridad=(int)(view.boxPrioridad.getSelectedIndex());
      
       usuarios.put(view.txtcorreo.getText(),new Solicitante(view.txtcorreo.getText(),view.txtcedula.getText(),view.txtnombre.getText(), view.txtapellido.getText(),view.txttelefono.getText(),view.txtcontrasenia.getText(),view.txtdependencia.getText(),prioridad+1));
           
         JOptionPane.showMessageDialog(null, "se guardo con solicitante");
           
           }
            if(view.boxTipo.getSelectedItem()=="ADMINISTRADOR"){
              prioridad=(int)(view.boxPrioridad.getSelectedIndex());//para tomar la prioridad
           Administrador.setCedula(view.txtcedula.getText());
           Administrador.setNombre(view.txtnombre.getText());
           Administrador.setApellido(view.txtapellido.getText());
           Administrador.setCorreo(view.txtcorreo.getText());
           Administrador.setTelefono(view.txttelefono.getText());
           Administrador.setContrasenia(view.txtcontrasenia.getText());
           Administrador.setDependencia(view.txtdependencia.getText());
           Administrador.setPrioridad(prioridad+1);
           usuarios.put(Administrador.getCorreo(), Administrador);
           JOptionPane.showMessageDialog(null, "se guardo como administrador");
         
           }
             if(view.boxTipo.getSelectedItem()=="AUTORIZADOR"){
                  prioridad=(int)(view.boxPrioridad.getSelectedIndex());//toma la prioridad
           autorizador.setCedula(view.txtcedula.getText());
           autorizador.setNombre(view.txtnombre.getText());
           autorizador.setApellido(view.txtapellido.getText());
           autorizador.setCorreo(view.txtcorreo.getText());
           autorizador.setTelefono(view.txttelefono.getText());
           autorizador.setContrasenia(view.txtcontrasenia.getText());
           autorizador.setDependencia(view.txtdependencia.getText());
           autorizador.setPrioridad(prioridad+1);
           usuarios.put(view.txtcorreo.getText(), autorizador);
           JOptionPane.showMessageDialog(null, "se guardo como autorizador");
          
           }
                if(view.boxTipo.getSelectedItem()=="USUARIO"){
           usuario.setCedula(view.txtcedula.getText());
           usuario.setNombre(view.txtnombre.getText());
           usuario.setApellido(view.txtapellido.getText());
           usuario.setCorreo(view.txtcorreo.getText());
           usuario.setTelefono(view.txttelefono.getText());
           usuario.setContrasenia(view.txtcontrasenia.getText());
           usuario.setDependencia(view.txtdependencia.getText());
           usuarios.put(view.txtcorreo.getText(), usuario);
            JOptionPane.showMessageDialog(null, "se guardo como usuario");
                }
                limpiarTextos();
           ActivarTextos(false);
           view.txtcorreo.setEditable(true);
            view.btnNuevo.setEnabled(true);
            view.btnGuardar.setEnabled(false);
            ActualizarArchivo();
            JOptionPane.showMessageDialog(null, "SE GUARDO EL NUEVO USUARIO");
           }
      }else if(bandera==-1){//si es editar  
          
           if(view.boxTipo.getSelectedItem()=="SOLICITANTE"){
         Usuario p;//temporal para usar el contructor
         prioridad=(int)(view.boxPrioridad.getSelectedIndex());
      
       usuarios.replace(view.txtcorreo.getText(),new Solicitante(view.txtcorreo.getText(),view.txtcedula.getText(),view.txtnombre.getText(), view.txtapellido.getText(),view.txttelefono.getText(),view.txtcontrasenia.getText(),view.txtdependencia.getText(),prioridad+1));
           
         JOptionPane.showMessageDialog(null, "se guardo con solicitante");
           
           }
            if(view.boxTipo.getSelectedItem()=="ADMINISTRADOR"){
              prioridad=(int)(view.boxPrioridad.getSelectedIndex());//para tomar la prioridad
           Administrador.setCedula(view.txtcedula.getText());
           Administrador.setNombre(view.txtnombre.getText());
           Administrador.setApellido(view.txtapellido.getText());
           Administrador.setCorreo(view.txtcorreo.getText());
           Administrador.setTelefono(view.txttelefono.getText());
           Administrador.setContrasenia(view.txtcontrasenia.getText());
           Administrador.setDependencia(view.txtdependencia.getText());
           Administrador.setPrioridad(prioridad+1);
           usuarios.replace(Administrador.getCorreo(), Administrador);
           JOptionPane.showMessageDialog(null, "se guardo como administrador");
         
           }
             if(view.boxTipo.getSelectedItem()=="AUTORIZADOR"){
                  prioridad=(int)(view.boxPrioridad.getSelectedIndex());//toma la prioridad
           autorizador.setCedula(view.txtcedula.getText());
           autorizador.setNombre(view.txtnombre.getText());
           autorizador.setApellido(view.txtapellido.getText());
           autorizador.setCorreo(view.txtcorreo.getText());
           autorizador.setTelefono(view.txttelefono.getText());
           autorizador.setContrasenia(view.txtcontrasenia.getText());
           autorizador.setDependencia(view.txtdependencia.getText());
           autorizador.setPrioridad(prioridad+1);
           usuarios.replace(view.txtcorreo.getText(), autorizador);
           JOptionPane.showMessageDialog(null, "se guardo como autorizador");
          
           }
                if(view.boxTipo.getSelectedItem()=="USUARIO"){
           usuario.setCedula(view.txtcedula.getText());
           usuario.setNombre(view.txtnombre.getText());
           usuario.setApellido(view.txtapellido.getText());
           usuario.setCorreo(view.txtcorreo.getText());
           usuario.setTelefono(view.txttelefono.getText());
           usuario.setContrasenia(view.txtcontrasenia.getText());
           usuario.setDependencia(view.txtdependencia.getText());
           //usuarios.remove(view.txtcorreo.getText());
           usuarios.put(view.txtcorreo.getText(), usuario);
           JOptionPane.showMessageDialog(null, "se guardo como usuario");
                }
                limpiarTextos();
           ActivarTextos(false);
            view.txtcorreo.setEditable(true);
            view.btnNuevo.setEnabled(true);
             view.btnGuardar.setEnabled(false);
             ActualizarArchivo();
             JOptionPane.showMessageDialog(null, "SE EDITO CORRECTAMENTE");
      }//////fin editarrrrr
  }
  
  
  
  public void Eliminar(){
      if(usuarios.containsKey(view.txtcorreo.getText())){
          usuarios.remove(view.txtcorreo.getText());
          limpiarTextos();
          JOptionPane.showMessageDialog(null, "EL USUARIO SE ELIMINO CORRECTAMENTE");
          ActivarBotones(false);
          ActualizarArchivo();
      }else{
          JOptionPane.showMessageDialog(null, "NO EXISTE EL USUARIO");
      
      }
     
      
  }
   public void ActivarBotones(Boolean estado){
        view.btnNuevo.setEnabled(true);
        view.btnEditar.setEnabled(estado);
       //view.btnCerrar.setEnabled(estado);
     view.btnGuardar.setEnabled(estado);
     view.btnEliminar.setEnabled(estado);
    
    }
   public void controlBox(){
   if(view.boxTipo.getSelectedItem()=="USUARIO"){
       System.out.println("entre");
       view.boxPrioridad.setEnabled(false);
   }else{
        view.boxPrioridad.setEnabled(true);
   }
}
   public void ActivarTextos(boolean estado){
       view.txtcedula.setEnabled(estado);
       view.txtnombre.setEnabled(estado);
       view.txtapellido.setEnabled(estado);
       view.txtcontrasenia.setEnabled(estado);
       view.txttelefono.setEnabled(estado);
       view.txtdependencia.setEnabled(estado);
       view.boxTipo.setEnabled(estado);
       //view.boxPrioridad.setEnabled(estado);
       
   }
   
    public void limpiarTextos(){
        view.txtcorreo.setText("");
       view.txtcedula.setText("");
       view.txtnombre.setText("");
       view.txtapellido.setText("");
       view.txtcontrasenia.setText("");
       view.txttelefono.setText("");
       view.txtdependencia.setText("");
   }
    public void ActualizarParametros(Usuario usuario,String key){
        usuarios.replace(key, usuario);
        ActualizarArchivo();
    }
    public boolean ValidarCorreo(){//validar extencion    
  Pattern pattern = Pattern.compile("(\\W|^)[\\w.\\-]{0,25}@(ucuenca.edu)\\.ec(\\W|$)");
        Matcher matcher = pattern.matcher(view.txtcorreo.getText());
        return matcher.find();
    }
    public boolean comprobarCampos(){
        if(view.txtcedula.getText().length()==0 || view.txtnombre.getText().length()==0 || view.txtapellido.getText().length()==0 ||view.txttelefono.getText().length()==0
               || view.txtcorreo.getText().length()==0 || view.txtdependencia.getText().length()==0   ){//ismepty india si un string esta vacio devuele true y si esta llano devuelve false
           //JOptionPane.showMessageDialog(null, "Llene todos los campos");
         
           return false;
       }
        return true;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        int prioridad;//para almacenar la prioridad
       if(e.getSource()==view.btnGuardar){
          if(!(ValidarCorreo())){
               JOptionPane.showMessageDialog(null, "CORREO INVALIDO USE LA EXTENCION @ucuenca.edu.ec");
          }else{
           if(comprobarCampos()){ 
                 grabar();   
           }
           else{
             JOptionPane.showMessageDialog(null, "Llene todos los campos");
                       }
          }
           }  
       
       if(e.getSource()==view.btnNuevo){
              this.bandera=1;
          ActivarTextos(true);
          limpiarTextos();
          view.btnGuardar.setEnabled(true);
          view.btnEditar.setEnabled(false);
           view.btnNuevo.setEnabled(false);
            view.btnEliminar.setEnabled(false);
            controlBox();
          //view.txtcedula.setText(usuarios.v);
       }
       if(e.getSource()==view.btnEditar){
           this.bandera=-1;
           ActivarTextos(true);
           view.txtcorreo.setEditable(false);
           view.btnEditar.setEnabled(false);
           view.btnGuardar.setEnabled(true);
           view.btnNuevo.setEnabled(false);
           view.btnEliminar.setEnabled(false);
           controlBox();
           
       }
         if(e.getSource()==view.btnCancelar){
           ActivarTextos(false);
           limpiarTextos();
           view.txtcorreo.setEditable(true);
           view.boxPrioridad.setEnabled(false);
          ActivarBotones(false);
           
       }
         if(e.getSource()==view.btnEliminar){
              
             Eliminar();
              controlBox();
            
         }
       if(e.getSource()==view.boxTipo){
            System.out.println("entre");
           controlBox();
       }
       // GrabarDatosArchivo();
    }
    
    @Override
    public void keyTyped(KeyEvent e) {//para buscar el usuario
        int tecla;
        
        tecla=e.getKeyChar();
       
        if(tecla==10 && e.getSource()==view.txtcorreo && view.btnNuevo.isEnabled()){//si oprimo enter
            view.txtprioridad.setText("");//para borrar al inicio
            if(usuarios.containsKey(view.txtcorreo.getText())){
                  view.btnEditar.setEnabled(true);
                  view.btnEliminar.setEnabled(true);
                 // view.boxPrioridad.setEditable(false);
           usuario=usuarios.get(view.txtcorreo.getText());//tomamos el usuario con ese correo
           view.txtnombre.setText(usuario.getNombre());
           view.txtapellido.setText(usuario.getApellido());
           view.txtcedula.setText(usuario.getCedula());
            view.txttelefono.setText(usuario.getTelefono());
            view.txtcontrasenia.setText(usuario.getContrasenia());
            view.txtdependencia.setText(usuario.getDependencia());
            ///si los usuarios se instanciaron en diferentes clases
             if(usuario instanceof Administrador){
           //Administrador=(Administrador)usuarios.get(view.txtcorreo.getText());
           Administrador=(Autorizador)usuarios.get(view.txtcorreo.getText());
           view.txtprioridad.setText(String.valueOf(Administrador.getPrioridad()));
           view.boxTipo.setSelectedItem("ADMINISTRADOR");
           view.boxPrioridad.setSelectedIndex(Administrador.getPrioridad()-1);//para saleccionar la prioridad en el box
           
            return;
             }
              if(usuario instanceof Autorizador){
            //autorizador=(Autorizador)usuarios.get(view.txtcorreo.getText());
            autorizador=(Solicitante)usuarios.get(view.txtcorreo.getText());
            view.txtprioridad.setText(String.valueOf(autorizador.getPrioridad()));
            view.boxTipo.setSelectedItem("AUTORIZADOR");
             view.boxPrioridad.setSelectedIndex(autorizador.getPrioridad()-1);
            return;
        }
            if(usuario instanceof Solicitante){
         solicitante=(Solicitante) usuarios.get(view.txtcorreo.getText());
         view.txtprioridad.setText(String.valueOf(solicitante.getPrioridad()));
         view.boxTipo.setSelectedItem("SOLICITANTE");
         view.boxPrioridad.setSelectedIndex(solicitante.getPrioridad()-1);
        
            return;
        } 
             if(usuario instanceof Usuario){
         //view.txtprioridad.setText(String.valueOf(solicitante.getPrioridad()));
         view.boxTipo.setSelectedItem("USUARIO");
          view.boxPrioridad.setSelectedIndex(0-1);
        
            return;
        } 
            ////fin estancia
           
                   }else{
            JOptionPane.showMessageDialog(null, "El usuario ingresado no existe", "Error", 2);
            view.btnEditar.setEnabled(false);
        }   
        }
         view.boxPrioridad.setEnabled(false);
    }

    @Override
    public void keyPressed(KeyEvent e) {
       
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
 //Evento para los textField de Palabra Clave
    private class EventoNumeros extends KeyAdapter
    {
        @Override
        public void keyTyped(KeyEvent e) {
            //Código ASCII de la tecla pulsada
             int tecla;
        tecla=e.getKeyChar();
       if(tecla<'0' || tecla>'9' || (tecla == KeyEvent.VK_BACK_SPACE)){
           e.consume();//hace que esa pulsacion de tecla se rechace
           
       }
        }
    }
    //Evento para los box
   
    
}
