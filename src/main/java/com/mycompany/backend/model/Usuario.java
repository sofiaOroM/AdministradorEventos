/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.backend.model;

/**
 *
 * @author sofia
 */
public class Usuario{
    private int id;
    private String nombre;
    private String password;
    private String correo;
    private String rol; // 'admin_sistema','admin_congreso','participante'
    private boolean activo;
    private byte[] foto;
    private String organizacion;
    private String identificacion;
    private String telefono;
    

    public Usuario(){
    }
    
    
    // getters / setters
    public int getId(){
        return id;
    }
    public void setId(Integer id){
        this.id = id;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public String getPassword(){
        return password;
    }
    
    public void setPassword(String password){    
        this.password = password;
    }
    
    public String getCorreo(){
        return correo;
    }
    
    public void setCorreo(String correo){
        this.correo = correo;
    }
    
    public String getRol(){
        return rol;
    }
    
    public void setRol(String rol){
        this.rol = rol;
    }
    
    public boolean isActivo(){
        return activo;
    }
    
    public void setActivo(boolean activo){
        this.activo = activo;
    }
    
    public byte[] getFoto(){
        return foto;
    }
    
    public void setFoto(byte[] foto){
        this.foto = foto;
    }
    
    public String getOrganizacion(){
        return organizacion;
    }
    
    public void setOrganizacion(String organizacion){
        this.organizacion = organizacion;
    }
    
    public String getIdentificacion(){
        return identificacion;
    }
    
    public void setIdentificacion(String identificacion){
        this.identificacion = identificacion;
    }
    
    public String getTelefono(){
        return telefono;
    }
    
    public void setTelefono(String telefono){
        this.telefono = telefono;
    }
    
}

