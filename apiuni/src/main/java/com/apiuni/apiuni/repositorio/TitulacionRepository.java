package com.apiuni.apiuni.repositorio;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.apiuni.apiuni.modelo.Titulacion;

@Repository
public interface TitulacionRepository extends CrudRepository<Titulacion,Long>{
	
	public abstract List<Titulacion> findByNombre(String nombre);


}
