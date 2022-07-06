/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;
import modelo.*;
/**
 *
 * @author Jorge
 */
public class Edificio {
    private String codigo;
    private String nombre;
    private String ubicacion;
    private int numPlantas;
    private int numEspacios;
    private ArrayList <Espacio> listaEspacios;// se pueden guardar estos en un hash map para que no se repitan

    public Edificio(String codigo, String nombre, String ubicacion, int numPlantas, int mumEspacios) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.numPlantas = numPlantas;
        this.numEspacios = numEspacios;
        listaEspacios= new ArrayList<Espacio>();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getMumEspacios() {
        return numEspacios;
    }

    public void setMumEspacios(int mumEspacios) {
        this.numEspacios = mumEspacios;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumEspacios() {
        return numEspacios;
    }

    public void setNumEspacios(int numEspacios) {
        this.numEspacios = numEspacios;
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
    
    public Espacio obtenerEspacio(int i){
       return listaEspacios.get(i);
    }
    
}
