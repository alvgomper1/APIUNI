package com.apiuni.apiuni.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.apiuni.apiuni.modelo.Asignatura;
import com.apiuni.apiuni.modelo.Departamento;
 

@Repository
public interface AsignaturaRepository extends CrudRepository<Asignatura,Long>{
	
	public abstract List<Asignatura> findByDepartamento(Departamento d);
	
	
	@Query(value = "SELECT rand() FROM Asignatura")
	public Asignatura findAleatorio();

}
