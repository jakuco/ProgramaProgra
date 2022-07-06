
package modelo;

import java.io.Serializable;

public class Usuario implements Serializable{
   private String cedula;
   private String contrasenia;
   private String nombre;
   private String apellido;
   private String telefono;
   private String correo;
   private String dependencia; 
   public Usuario(){
       
   }

    public Usuario(String cedula, String contrasenia, String nombre, String apellido, String telefono, String correo, String dependencia) {
        this.cedula = cedula;
        this.contrasenia = contrasenia;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.correo = correo;
        this.dependencia = dependencia;
    }

    public String getCedula() {
        return cedula;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public String getDependencia() {
        return dependencia;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setDependencia(String dependencia) {
        this.dependencia = dependencia;
    }

}
