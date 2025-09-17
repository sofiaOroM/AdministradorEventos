/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.backend.model;

import java.time.LocalTime;

/**
 *
 * @author sofia
 */
public class Actividad {
    private int id;
    private String nombre;
    private String descripcion;
    private String tipo; // ENUM: PONENCIA o TALLER
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private int salonId;
    private String salonNombre;
    private String congresoId;
    private int cupo;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public int getSalonId() {
        return salonId;
    }

    public void setSalonId(int salonId) {
        this.salonId = salonId;
    }

    public String getSalonNombre() {
        return salonNombre;
    }

    public void setSalonNombre(String salonNombre) {
        this.salonNombre = salonNombre;
    }

    public String getCongresoId() {
        return congresoId;
    }

    public void setCongresoId(String congresoId) {
        this.congresoId = congresoId;
    }

    public int getCupo() {
        return cupo;
    }

    public void setCupo(int cupo) {
        this.cupo = cupo;
    }
}