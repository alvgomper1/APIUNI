package com.apiuni.apiuni.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiuni.apiuni.modelo.Titulacion;
import com.apiuni.apiuni.repositorio.TitulacionRepository;

@Service
public class TitulacionService {

	
	@Autowired
	private TitulacionRepository titulacionRepository;
	
	public long saveId(Titulacion t) {
		return this.titulacionRepository.save(t).getId();
	}
	
	public Titulacion findById(long id) {
		
		return this.titulacionRepository.findById(id).orElse(null);
	}



	public Iterable<Titulacion> findAll() {
		 
		return titulacionRepository.findAll();
	}
	
}
