package com.apiuni.apiuni.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiuni.apiuni.modelo.Alumno;
import com.apiuni.apiuni.repositorio.AlumnoRepository;

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

	public void save(Alumno a) {
		alumnoRepository.save(a);
	}

	public long saveId(Alumno d) {
		return this.alumnoRepository.save(d).getId();
	}

	public boolean eliminaAlumnoPorId(long id) {

		
		try {
			this.alumnoRepository.deleteById(id);
			return true;
		} catch (Exception err) {
			return false;
		}

	}

}
