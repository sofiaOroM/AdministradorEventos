/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.backend.model.reportes;

import java.util.List;

/**
 *
 * @author sofia
 */
public class ReporteReservaTaller {
    private String nombre;
    private int cupo;
    private int reservas;
    private int disponibles;
    private List<ReporteParticipante> participantes;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCupo() {
        return cupo;
    }

    public void setCupo(int cupo) {
        this.cupo = cupo;
    }

    public int getReservas() {
        return reservas;
    }

    public void setReservas(int reservas) {
        this.reservas = reservas;
    }

    public int getDisponibles() {
        return disponibles;
    }

    public void setDisponibles(int disponibles) {
        this.disponibles = disponibles;
    }

    public List<ReporteParticipante> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<ReporteParticipante> participantes) {
        this.participantes = participantes;
    }
}
