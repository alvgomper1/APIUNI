package com.apiuni.apiuni.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apiuni.apiuni.modelo.Profesor;
import com.apiuni.apiuni.servicio.ProfesorService;

@RestController
@RequestMapping("/profesores")
public class ProfesorController {
	
	@Autowired
	ProfesorService profesorService;
	
	@GetMapping()
    public List<Profesor> obtenerprofesores(){
        return this.profesorService.obtenerProfesores();
    }
	
	@PostMapping()
    public Profesor guardarprofesor(@RequestBody Profesor p){
        return this.profesorService.guardaProfesor(p);
    }
	
	@GetMapping(path = "/create")
	public String creaProfesor() {

		Profesor p = new Profesor();
		p.setNombre("Alvaro");
		p.setApellidos("Gomez Perez");
		p.setEmail("alvaro@gmail.com");
		p.setTelefono(654321234);
		this.profesorService.guardaProfesor(p);
		return "Se creó el profesor";
    }
	
	 @DeleteMapping( path = "/{id}")
	    public String eliminarPorId(@PathVariable("id") Long id){
	        boolean ok = this.profesorService.eliminaProfesorPorId(id);
	        if (ok){
	            return "Se eliminó el profesor con id " + id;
	        }else{
	            return "No pudo eliminar el profesor con id " + id;
	        }

}
}
