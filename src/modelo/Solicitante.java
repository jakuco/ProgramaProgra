
package modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author karl
 */
public class Solicitante extends Usuario{
    private Map<String,Solicitud> solicitudes;//La clave es el codigo de la solicitud
    private int prioridad;
   
   public Solicitante(){
      // super();
      solicitudes = new HashMap<String,Solicitud>();
     
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

    public ArrayList<Solicitud> getSolicitudes() {
        Collection<Solicitud> values = solicitudes.values();
        ArrayList<Solicitud> listaSolicitudes = new ArrayList<Solicitud>(values);
        return listaSolicitudes;
    }

    public void setSolicitudes(Map<String, Solicitud> solicitudes) {
        this.solicitudes = solicitudes;
    }
    
    public void removerSolicitud(String codigo){//Remueve la solicitud ingresando el codigo
        solicitudes.remove(codigo);
    }
    
    public Solicitud getSolicitud(String codigo){
        return solicitudes.get(codigo);
    }
    
    public void anadirmapa(String codigo, Solicitud solici){
        solicitudes.put(codigo,solici);
    }
    
}
