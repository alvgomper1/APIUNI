package com.apiuni.apiuni.repositorio;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.apiuni.apiuni.modelo.Alumno;
 

@Repository
public interface AlumnoRepository extends CrudRepository<Alumno,Long>{
	
	public abstract List<Alumno> findByTelefono(Integer telefono);


}
