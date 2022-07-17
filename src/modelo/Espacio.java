
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import java.util.*;

/**
 *
 * @author Jorge
 */
public class Espacio implements Serializable{
    private String codigo;
    private String nombre;
    private String descripcion;
    private String ubicacion;
    private int tipo;
    private int capacidad;
    private ArrayList <Elemento> listaElementos; 
    private Map<String,Agenda> agendas;
    private Queue<Solicitud> colaSolicitudes;

    public Espacio() {
        agendas = new HashMap<String,Agenda>();
        listaElementos = new ArrayList<>();
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    
    public ArrayList<Elemento> obtenerListaElementos(){
        return listaElementos;
    }
    
    public Agenda getAgenda(String fecha){
        return agendas.get(fecha);
    }
    
    public void setAgenda(String fecha, Agenda agenda){
            agendas.put(fecha, agenda);
    }
    
    public Solicitud dequeSolicitud (){
        return colaSolicitudes.peek();
    }
    
    public void enqueSolicitud (Solicitud solicitud){
        colaSolicitudes.add(solicitud);
    }
    
    public Queue<Solicitud> getSolicitudes(){
            return colaSolicitudes;
    }
    
}
