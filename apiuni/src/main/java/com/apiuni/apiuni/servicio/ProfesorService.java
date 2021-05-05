package com.apiuni.apiuni.servicio;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiuni.apiuni.modelo.Departamento;
import com.apiuni.apiuni.modelo.Profesor;
import com.apiuni.apiuni.repositorio.ProfesorRepository;

@Service
public class ProfesorService {
	@Autowired
	ProfesorRepository profesorRepository;
	
	public long saveId(Profesor p) {
		return this.profesorRepository.save(p).getId();
	}

	public List<Profesor> obtenerProfesores() {
		return (List<Profesor>) this.profesorRepository.findAll();
	}

	public Profesor guardaProfesor(Profesor p) {
		return this.profesorRepository.save(p);
	}

	public Profesor obtenerProfesorPorId(Long idProfesor) {
		return this.profesorRepository.findById(idProfesor).orElse(null);

	}

	public List<Profesor> obtenerProfesoresPorDepartamento(Departamento d) {
		return this.profesorRepository.findByDepartamento(d);

	}

	public boolean eliminaProfesor(Profesor p) {
		 try{
	            this.profesorRepository.delete(p);
	            return true;
	        }catch(Exception err){
	            return false;
	        }
	}

	public boolean eliminaProfesorPorId(Long idProfesor) {
		 try{
	            this.profesorRepository.deleteById(idProfesor);
	            return true;
	        }catch(Exception err){
	            return false;
	        }
	}
	
	public void saveAll(Set<Profesor> profesores) {
		
		this.profesorRepository.saveAll(profesores);
	}

}
