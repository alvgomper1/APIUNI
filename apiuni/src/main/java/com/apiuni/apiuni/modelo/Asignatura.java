package com.apiuni.apiuni.modelo;

import java.util.List;
import java.util.Set;

import javax.persistence.*;



@Entity
public class Asignatura {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Long id;
	private String nombre;

	private String caracter;

	private String duracion;

	private Integer creditos;
	
	private String ano;

	
	@ManyToOne
	@JoinColumn(name = "titulacion_id")
	private Titulacion titulacion;

	@ManyToOne
	@JoinColumn(name = "departamento_id")
	private Departamento departamento;

	
	@ManyToMany
	private List<Profesor> profesores;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "asignatura_alumno",
    joinColumns = @JoinColumn(name = "asignatura_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "alumno_id", referencedColumnName = "id"))
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
	
	

}
