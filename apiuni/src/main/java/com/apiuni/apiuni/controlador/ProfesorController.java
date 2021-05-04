package com.apiuni.apiuni.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apiuni.apiuni.modelo.ErrorObject;
import com.apiuni.apiuni.modelo.Profesor;
import com.apiuni.apiuni.repositorio.DepartamentoRepository;
import com.apiuni.apiuni.servicio.DepartamentoService;
import com.apiuni.apiuni.servicio.ProfesorService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import netscape.javascript.JSObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/profesores")
public class ProfesorController {

	@Autowired
	ProfesorService profesorService;

	@Autowired
	DepartamentoService departamentoService;

	@Operation(summary = "Obtener profesores", description = "Esta operacion devuelve todoslos profesores de la pagina web", tags = "Profesor")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se han obtenido todos los resultados de profesores de la base de datos correctamente", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = Profesor.class)),

							mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = "No disponible", content = @Content),
			@ApiResponse(responseCode = "400", description = "Solicitud erronea", content = @Content(schema = @Schema(implementation = ErrorObject.class))) })
	@GetMapping()
	public List<Profesor> obtenerprofesores() {
		return this.profesorService.obtenerProfesores();
	}

	@Operation(summary = "Crear profesor", description = "Esta operacion crea un nuevo profesor y lo inserta en la base de datos", tags = "Profesor")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se hacreado el profesor y se ha insertado en la base de datos correctamente", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = Profesor.class))) }),
			@ApiResponse(responseCode = "500", description = "No se ha podido crear el profesor porque se añadieron atributos que no estan creados en la base de datos", content = @Content),

			@ApiResponse(responseCode = "400", description = "Solicitud erronea", content = @Content(schema = @Schema(implementation = ErrorObject.class))) })
	@PostMapping(path = "/añadir", consumes = "application/json")
	public String guardarprofesor(@RequestBody Profesor p) {

		if(p.getDepartamento()!=null) {
		
		if (departamentoService.findById(p.getDepartamento().getId())== null) {
			p.setDepartamento(null);
			this.profesorService.guardaProfesor(p);
			return "No se ha encontrado el departamento, se ha creado el profesor con Departamento = null";}
		else {
			this.profesorService.guardaProfesor(p);
			return "Se ha creado el profesor con departamento = " + p.getDepartamento().getNombre();
		}
		
		} else {
			this.profesorService.guardaProfesor(p);
			return "Se ha añadido correctamente el profesor ";
		}

	}

	@Operation(summary = "Borrar profesor", description = "Esta operacion permite eliminar un profesor introduciendo como parametro su identificador (id)", tags = "Profesor")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se ha borrado el profesor de la base de datos correctamente", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = Profesor.class))) }),
			@ApiResponse(responseCode = "404", description = "No disponible", content = @Content),
			@ApiResponse(responseCode = "400", description = "Solicitud erronea", content = @Content(schema = @Schema(implementation = ErrorObject.class))) })

	@DeleteMapping(path = "/eliminar/{id}")
	public String eliminarPorId(@PathVariable("id") Long id) {
		boolean ok = this.profesorService.eliminaProfesorPorId(id);
		if (ok) {
			return "Se eliminó el profesor con id " + id;
		} else {
			return "No pudo eliminar el profesor con id " + id;
		}

	}


}
