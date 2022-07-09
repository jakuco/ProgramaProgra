/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import modelo.*;
/**
 *
 * @author Jorge
 */
public class Edificio implements Serializable{
    private String codigo;
    private String nombre;
    private String ubicacion;
    private int numPlantas;
    private int numEspacios;
    private ArrayList <Espacio> listaEspacios;// se pueden guardar estos en un hash map para que no se repitan

    public Edificio() {
        listaEspacios= new ArrayList<Espacio>();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getNumEspacios() {
        return numEspacios;
    }

    public void setNumEspacios(int numEspacios) {
        this.numEspacios = numEspacios;
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public int getNumPlantas() {
        return numPlantas;
    }

    public void setNumPlantas(int numPlantas) {
        this.numPlantas = numPlantas;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    
    public void addEspacio(Espacio e){
        listaEspacios.add(e);
    }
    
    public void removeEspacio(int i){
        listaEspacios.remove(i);
    }
    public Espacio obtenerEspacio(int i){
       return listaEspacios.get(i);
    }
    
    public ArrayList<Espacio> getListaEspacios(){
        return listaEspacios;
    }
    
}
