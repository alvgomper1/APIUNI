package com.apiuni.apiuni.modelo;

import java.util.Set;

import javax.persistence.*;

@Entity
public class Asignatura {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;
	private String nombre;
	private Integer creditos;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "asignatura_alumno",
    joinColumns = @JoinColumn(name = "asignatura_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "alumno_id", referencedColumnName = "id"))
	private Set<Alumno> alumnos;

	@ManyToOne
	private Departamento departamento;

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
	
	

}
