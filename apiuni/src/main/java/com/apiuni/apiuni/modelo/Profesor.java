package com.apiuni.apiuni.modelo;

import java.util.List;

import javax.persistence.*;

import io.swagger.v3.oas.annotations.Hidden;



@Entity
public class Profesor {
	 
	@Id
	@Column(nullable = false)
	private Long id;
	
	private String nombre;
    private String email;
    private String telefono;
    
    @ManyToOne
    private Departamento departamento;
    
    
    
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

	

    
    
    


}
