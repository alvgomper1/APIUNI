package com.apiuni.apiuni.servicio;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiuni.apiuni.modelo.Asignatura;
import com.apiuni.apiuni.repositorio.AsignaturaRepository;
 
@Service
public class AsignaturaService {

	
	@Autowired
	private AsignaturaRepository asignaturarepository;
	
	
	
	public Asignatura findById(long id) {
		
		return this.asignaturarepository.findById(id).orElse(null);
	}
	
public boolean existen(List<Asignatura> asignaturas) {
		Collection<Asignatura> a =   (Collection<Asignatura>) asignaturarepository.findAll();
		
		
		if (a.containsAll(asignaturas)) {
			return true;
		}
		
		else {
			return false;
		}
	}
	
	
}
