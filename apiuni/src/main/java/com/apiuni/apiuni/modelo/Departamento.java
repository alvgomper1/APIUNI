package com.apiuni.apiuni.modelo;

import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;




@Entity
public class Departamento {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( nullable = false)
	private Long id;
	
	private String nombre;
	
	private String sede;
	
	private String email;
	
	private String telefono;
	
	private String web;
	
	@OneToMany(mappedBy = "departamento",cascade = CascadeType.ALL)
	private Set<Asignatura> asignaturas;
	
	@OneToMany(mappedBy = "departamento")
	private Set<Profesor> profesores;
	
	
	
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
	public Set<Profesor> getProfesores() {
		return profesores;
	}
	public void setProfesores(Set<Profesor> profesores) {
		this.profesores = profesores;
	}
	public Set<Asignatura> getAsignaturas() {
		return asignaturas;
	}
	public void setAsignaturas(Set<Asignatura> asignaturas) {
		this.asignaturas = asignaturas;
	}
	public String getSede() {
		return sede;
	}
	public void setSede(String sede) {
		this.sede = sede;
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
	public String getWeb() {
		return web;
	}
	public void setWeb(String web) {
		this.web = web;
	}

}
