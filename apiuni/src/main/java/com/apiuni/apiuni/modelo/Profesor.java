package com.apiuni.apiuni.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;



@Entity
public class Profesor {
	 
	@Id
	@Column(nullable = false)
	private Long id;
	
	private String nombre;
    private String email;
    private String telefono;
    
    @JsonIgnore
    @ManyToOne
    private Departamento departamento;
    
    
    @JsonIgnore
    @ManyToMany(mappedBy = "profesores" ) 
	private List<Asignatura> asignaturas;  
    
    @PreRemove
    private void removeProfesoresFromAsignatura() {
        for (Asignatura u : asignaturas) {
            u.getProfesores().remove(this);
        }
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
	public Departamento getDepartamento() {
		return departamento;
	}
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	public List<Asignatura> getAsignaturas() {
		return asignaturas;
	}
	public void setAsignaturas(List<Asignatura> asignaturas) {
		this.asignaturas = asignaturas;
	}

	@Override
	public String toString() {
		return "Profesor [id=" + id + ", nombre=" + nombre + ", email=" + email + ", telefono=" + telefono
				+ ", departamento=" + departamento + ", asignaturas=" + asignaturas + "]";
	}

	
	@JsonProperty(value = "Asignaturas")
	public List<String> getAsignaturasProfesor(){
		return new ArrayList<>();
	}
	
	
	@JsonProperty(value = "Departamento")
	public String getDepartamentoProfesor(){
		return "Departamento";
	}
    
    
    


}
