package com.apiuni.apiuni.servicio;

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
	
}
