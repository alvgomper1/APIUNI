package com.apiuni.apiuni.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Asignatura {
	
	@Id
	@Column(unique = true, nullable = false)
	private Long id;
	private String nombre;

	private String caracter;

	private String duracion;

	private Integer creditos;

	private String ano;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "titulacion_id")
	private Titulacion titulacion;
	@JsonIgnore
	@ManyToOne
	private Departamento departamento;

	@JsonIgnore
	@ManyToMany()
	private List<Profesor> profesores;

	@JsonIgnore
	@ManyToMany()
	@JoinTable(name = "asignatura_alumnos", joinColumns = @JoinColumn(name = "FK_ASIGNATURA", nullable = false), inverseJoinColumns = @JoinColumn(name = "FK_ALUMNOS", nullable = false))
	private Set<Alumno> alumnos;

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

	public Integer getCreditos() {
		return creditos;
	}

	public void setCreditos(Integer creditos) {
		this.creditos = creditos;
	}

	public Set<Alumno> getAlumnos() {
		return alumnos;
	}

	public void setAlumnos(Set<Alumno> alumnos) {
		this.alumnos = alumnos;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public String getDuracion() {
		return duracion;
	}

	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}

	public String getCaracter() {
		return caracter;
	}

	public void setCaracter(String caracter) {
		this.caracter = caracter;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public List<Profesor> getProfesores() {
		return profesores;
	}

	public void setProfesores(List<Profesor> profesores) {
		this.profesores = profesores;
	}

	public Titulacion getTitulacion() {
		return titulacion;
	}

	public void setTitulacion(Titulacion titulacion) {
		this.titulacion = titulacion;
	}

	@JsonProperty(value = "Profesores")
	public List<String> getProfesoresAsignatura() {
		return new ArrayList<>();
	}

	@JsonProperty(value = "Alumnos")
	public List<String> getAlumnosAsignatura() {
		return new ArrayList<>();
	}

	@JsonProperty(value = "Titulacion")
	public String getTitulacionAsignatura() {
		return new String();
	}

	@JsonProperty(value = "Departamento")
	public String getDepartamentoAsignatura() {
		return new String();
	}

}
