package com.apiuni.apiuni.repositorio;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.apiuni.apiuni.modelo.Departamento;
 

@Repository
public interface DepartamentoRepository extends CrudRepository<Departamento,Long>{
	
	public abstract List<Departamento> findByNombre(String nombre);


}
