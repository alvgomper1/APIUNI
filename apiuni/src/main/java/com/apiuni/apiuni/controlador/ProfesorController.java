package com.apiuni.apiuni.controlador;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.apiuni.apiuni.modelo.Asignatura;
import com.apiuni.apiuni.modelo.ErrorObject400;
import com.apiuni.apiuni.modelo.ErrorObject404;
import com.apiuni.apiuni.modelo.ErrorObject409;
import com.apiuni.apiuni.modelo.Profesor;
import com.apiuni.apiuni.modeloRequestBody.ProfesorRequest;
import com.apiuni.apiuni.servicio.AsignaturaService;
import com.apiuni.apiuni.servicio.DepartamentoService;
import com.apiuni.apiuni.servicio.ProfesorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/profesores")
public class ProfesorController {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	ProfesorService profesorService;

	@Autowired
	AsignaturaService asignaturaService;

	@Autowired
	DepartamentoService departamentoService;

	@Operation(summary = "Obtener profesores", description = "Esta operacion devuelve todos los profesores de la pagina web", tags = "Profesor")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se han obtenido todos los resultados de profesores de la base de datos correctamente", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = Profesor.class)),
 
							mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = "No se ha encontrado el profesor con ese id", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject404.class)), mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Solicitud erronea", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject400.class)), mediaType = "application/json")) })
	@GetMapping()
	public String obtenerprofesores() throws JsonProcessingException {
		return objectMapper.writeValueAsString(this.profesorService.obtenerProfesores());

	}

	@Operation(summary = "Obtener profesores por nombre", description = "Esta operacion devuelve todos los profesores de la pagina web que tengan el nombre introducido como parametro", tags = "Profesor")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se han obtenido todos los resultados de profesores de la base de datos correctamente", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = Profesor.class)),

							mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = "No se ha encontrado el profesor con ese id", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject404.class)), mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Solicitud erronea", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject400.class)), mediaType = "application/json")) })
	@GetMapping("{nombre}")
	public String obtenerProfesorPorNombre(@PathVariable("nombre") final String nombre) throws JsonProcessingException {
		return objectMapper.writeValueAsString(this.profesorService.obtenerProfesores().stream()
				.filter(x -> x.getNombre().startsWith(nombre.toUpperCase())).collect(Collectors.toList()));

	}

	@Operation(summary = "Crear profesor", description = "Esta operacion crea un nuevo profesor y lo inserta en la base de datos", tags = "Profesor")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se ha ejecutado la consulta correctamente", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = ProfesorRequest.class))) }),
			@ApiResponse(responseCode = "500", description = "No se ha podido crear el profesor porque se a??adieron atributos que no estan creados en la base de datos", content = @Content),
			@ApiResponse(responseCode = "404", description = "No se ha encontrado el profesor con ese id", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject404.class)), mediaType = "application/json") }),
			@ApiResponse(responseCode = "409", description = "Ya existe un alumno con ese id que se muestra en la respuesta", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject409.class)), mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Solicitud erronea", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject400.class)), mediaType = "application/json")) })
	@PostMapping(path = "/a??adir", consumes = "application/json")
	public ResponseEntity<Profesor> guardarprofesor(@RequestBody ProfesorRequest p) {

		
		if(p.getId()<0) {
			return new ResponseEntity<Profesor>(HttpStatus.BAD_REQUEST);
		}
		
		if(this.profesorService.obtenerProfesorPorId(p.getId())!=null) {
			return new ResponseEntity<Profesor>(this.profesorService.obtenerProfesorPorId(p.getId()),HttpStatus.CONFLICT);
		}else {
			
			Profesor profesor = new Profesor();
			profesor.setAsignaturas(new ArrayList<Asignatura>());
			profesor.setDepartamento(null);
			profesor.setEmail(p.getEmail());
			profesor.setId(p.getId());
			profesor.setNombre(p.getNombre());
			profesor.setTelefono(p.getTelefono());
			profesorService.saveId(profesor);
			
			return new ResponseEntity<Profesor>(profesor,HttpStatus.OK);
		}

	}

	@Operation(summary = "Borrar profesor", description = "Esta operacion permite eliminar un profesor introduciendo como parametro su identificador (id)", tags = "Profesor")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se ha borrado el profesor de la base de datos correctamente", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = Profesor.class))) }),

			@ApiResponse(responseCode = "404", description = "No se ha encontrado el profesor con ese id", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject404.class)), mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Solicitud erronea", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject400.class)), mediaType = "application/json")) })

	@DeleteMapping(path = "/eliminar/{id}")
	public ResponseEntity<Profesor> eliminarPorId(@PathVariable("id") Long id) {
		
		boolean ok = this.profesorService.eliminaProfesorPorId(id);
		if (ok) {
			
			return new ResponseEntity<Profesor>(HttpStatus.OK);

		} else {
			return new ResponseEntity<Profesor>(HttpStatus.NOT_FOUND);

		}

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Operation(summary = "A??adir asignatura al profesor", description = "Esta operaci??n  a??ade una asignatura al profesor", tags = "Profesor")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se ha ejecutado la consulta correctamente", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = Profesor.class))) }),
			@ApiResponse(responseCode = "500", description = "No se ha podido a??adir la asignatura", content = @Content),
			@ApiResponse(responseCode = "404", description = "No se ha encontrado la asignatura o el profesor con ese id", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject404.class)),	mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Solicitud err??nea", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject404.class)),	mediaType = "application/json")) })
	@PutMapping("{idProfesor}/a??adirAsignatura/{idAsignatura}")
	public ResponseEntity<Profesor> anadeAsignaturaPorId(@PathVariable("idAsignatura") final long idAsignatura,
			@PathVariable("idProfesor") final long idProfesor)   {

		Asignatura asig = asignaturaService.findById(idAsignatura);
		Profesor p = profesorService.obtenerProfesorPorId(idProfesor);
		if (asig == null || p == null) {
			return new ResponseEntity<Profesor>(HttpStatus.NOT_FOUND);
		} else {
			   
			asig.getProfesores().add(p);
			p.getAsignaturas().add(asig);

			profesorService.saveId(p);
			asignaturaService.saveId(asig);
			return new ResponseEntity<Profesor>(p,HttpStatus.OK);
		}

	}
	
	
	
	
	

}
