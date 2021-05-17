package com.apiuni.apiuni.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiuni.apiuni.modelo.Departamento;
import com.apiuni.apiuni.repositorio.DepartamentoRepository;

@Service
public class DepartamentoService {

	
	@Autowired
	private DepartamentoRepository departamentoRepository;
	
	
	
	public Departamento findById(long id) {
		
		return this.departamentoRepository.findById(id).orElse(null);
	}
	
	public List<Departamento> findAll (){
		return (List<Departamento>) this.departamentoRepository.findAll();
	}
	
	public void save(Departamento d) {
		
		
		this.departamentoRepository.save(d);
	}
	
	public long saveId(Departamento d) {
		return this.departamentoRepository.save(d).getId();
	}
	
	public boolean eliminaDepartamentoPorId(Long idDepartamento) {
		 try{
	           this.departamentoRepository.deleteById(idDepartamento);
	           return true;
	       }catch(Exception err){
	           return false;
	       }
	}
	
}
