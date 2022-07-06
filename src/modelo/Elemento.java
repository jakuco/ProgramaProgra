/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Jorge
 */
public class Elemento {
    private String descripcion;
    private int cantidad;
    public Elemento (String descripcion,int cantidad){
        this.descripcion=descripcion;
        this.cantidad=cantidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    
    public String getDescripcion() {
        return descripcion;
    } 

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
   
}
