
package modelo;

/**
 *
 * @author karl
 */
public class Solicitante extends Usuario{
   private int prioridad;
   public Solicitante(){
      // super();
   }
    public Solicitante(String correo, String cedula, String nombre, String apellido, String telefono,String contrasenia, String dependencia,int prioridad){
      super(correo,cedula,nombre,apellido,telefono,contrasenia,dependencia);
      this.prioridad=prioridad;
   } 
   

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public int getPrioridad() {
        return prioridad;
    }
    
}
