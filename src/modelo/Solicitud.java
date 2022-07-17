/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;

/**
 *
 * @author davqi
 */
public class Solicitud implements Serializable{
    private String codigo;
    private String estado;
    private String sala;
    private String fecha;
    private LineaAgenda linea;
    
    public Solicitud (){
       
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getEstado() {
        return estado;
    }

    public String getSala() {
        return sala;
    }

    public LineaAgenda getLinea() {
        return linea;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public void setLinea(LineaAgenda linea) {
        this.linea = linea;
    }
    
}
