/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
/**
 *
 * @author sofia
 */
public class TokenCambio {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String token;

    @ManyToOne
    @JoinColumn(name="usuario_id", nullable=false)
    private Usuario usuario;

    @Column(nullable=false)
    private LocalDateTime expiracion;

    private boolean usado = false;

    // getters/setters
    public Long getId(){
        return id;
    }
    
    public void setId(Long id){
        this.id = id;
    }
    
    public String getToken(){
        return token;
    }
    
    public void setToken(String token){
        this.token = token;
    }
    
    public Usuario getUsuario(){
        return usuario;
    }
    
    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }
    
    public LocalDateTime getExpiracion(){
        return expiracion;
    }
    
    public void setExpiracion(LocalDateTime expiracion){
        this.expiracion = expiracion;
    }
    
    public boolean isUsado(){
        return usado;
    }
    
    public void setUsado(boolean usado){
        this.usado = usado;
    }
}
