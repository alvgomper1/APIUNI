package com.apiuni.apiuni.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiuni.apiuni.modelo.Alumno;
import com.apiuni.apiuni.modelo.Asignatura;
import com.apiuni.apiuni.modelo.Departamento;
import com.apiuni.apiuni.repositorio.AlumnoRepository;
import com.apiuni.apiuni.repositorio.AsignaturaRepository;

@Service
public class AlumnoService {

	@Autowired
	private AlumnoRepository alumnoRepository;
	
	
	public List<Alumno> findAllAlumno() {
		
		return (List<Alumno>) this.alumnoRepository.findAll();
	}
	
	public Alumno findAlumnoById(long id) {
		return this.alumnoRepository.findById(id).orElse(null);
	}
	
	
}
