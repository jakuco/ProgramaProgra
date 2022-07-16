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
public class Agenda implements Serializable{
    private String fecha;
    private LineaAgenda[] agenda;
    int cont;

    public Agenda(){
        agenda = new LineaAgenda[16];
        cont = 0;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public LineaAgenda[] getAgenda() {
        return agenda;
    }
    
    public int getLineas(){
        return agenda.length;
    }

    public void setAgenda(LineaAgenda[] agenda) {
        this.agenda = agenda;
    }
    
    public void addLineaAgenda(LineaAgenda linea){
        agenda[cont] = linea;
        cont++;
    }
}
