package com.apiuni.apiuni.repositorio;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.apiuni.apiuni.modelo.Departamento;
import com.apiuni.apiuni.modelo.Profesor;

@Repository
public interface ProfesorRepository extends CrudRepository<Profesor,Long>{
	
	public abstract List<Profesor> findByDepartamento(Departamento d);


}
