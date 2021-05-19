package com.apiuni.apiuni.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PreRemove;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.Hidden;

 

@Entity
public class Alumno {
	
	  
	  
	  
	
	@Id
	@Column(unique = true, nullable = false)
	 
	private Long id; 
	
	private String nombre;
	private String apellidos;
	private Integer edad;
    private String email;
    
    private String telefono;
    
    @ManyToMany(mappedBy = "alumnos" ) 
    @JsonIgnore()
	private List<Asignatura> asignaturas;  
    
    @PreRemove
    private void removeAlumnosFromAsignatura() {
        for (Asignatura u : asignaturas) {
            u.getAlumnos().remove(this);
        }
    }
    
    @JsonProperty(value = "Asignaturas")
	public List<String> getAsignaturasAlumno(){
		return new ArrayList<>();
	}

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

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	
	 

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	
	public List<Asignatura> getAsignaturas() {
		return asignaturas;
	}

	public void setAsignaturas(List<Asignatura> asignaturas) {
		this.asignaturas = asignaturas;
	}
    
	

}
