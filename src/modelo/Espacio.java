/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.*;

/**
 *
 * @author Jorge
 */
public class Espacio {
    private String codigo;
    private String descripcion;
    private String ubicacion;
    private int tipo;
    private int capacidad;
    private ArrayList <Elemento> listaElementos; 
    private Queue<Solicitud_Borrar> colaSolicitudes;

    public Espacio(String codigo, String descripcion, String ubicacion, int tipo, int capacidad) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.tipo = tipo;
        this.capacidad = capacidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
    
    public void addElemento(Elemento e){
        listaElementos.add(e);
    }
    
    public Elemento getElemento(int indice){
        return listaElementos.get(indice);
    }
    
    public Solicitud_Borrar dequeSolicitud (){
        return colaSolicitudes.peek();
    }
    
    public void enqueSolicitud (Solicitud_Borrar solicitud){
        colaSolicitudes.add(solicitud);
    }
}
