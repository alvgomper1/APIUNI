package com.apiuni.apiuni.modelo;

import java.util.List;

import javax.persistence.*;

import io.swagger.v3.oas.annotations.Hidden;


public class ProfesorRequest {
	 
	
	private Long id;
	
	private String nombre;
    private String email;
    private String telefono;
    
    
	public Long getId() {
		return id;  
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	

	

    
    
    


}
