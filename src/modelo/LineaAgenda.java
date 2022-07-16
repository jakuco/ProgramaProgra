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
public class LineaAgenda implements Serializable{
    private String horaInicial;
    private String horaFinal;
    private String actividad;
    private String responsable;
    
    public LineaAgenda(){
        
    }

    public String getHoraInicial() {
        return horaInicial;
    }

    public String getHoraFinal() {
        return horaFinal;
    }

    public String getActividad() {
        return actividad;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setHoraInicial(String horaInicial) {
        this.horaInicial = horaInicial;
    }

    public void setHoraFinal(String horaFinal) {
        this.horaFinal = horaFinal;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }
    
}
