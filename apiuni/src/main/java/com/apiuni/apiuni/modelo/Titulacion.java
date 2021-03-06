package com.apiuni.apiuni.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "titulaciones")
public class Titulacion {

	@Id
	@Column(nullable = false)
	private Long id;

	@Column(unique = true)
	private String nombre;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "titulacion")
	private List<Asignatura> asignaturas;

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

	public List<Asignatura> getAsignaturas() {
		return asignaturas;
	}

	public void setAsignaturas(List<Asignatura> asignaturas) {
		this.asignaturas = asignaturas;
	}
	
	@JsonProperty(value = "Asignaturas")
	public List<String> getAsignaturasTitulacion() {
		return new ArrayList<>();
	}

}
