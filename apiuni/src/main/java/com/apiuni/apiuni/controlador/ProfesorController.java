package com.apiuni.apiuni.controlador;

import java.util.stream.Collectors;

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
					@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject.class))) }),
			@ApiResponse(responseCode = "400", description = "Solicitud erronea", content = @Content(schema = @Schema(implementation = ErrorObject.class))) })
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
					@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject.class))) }),
			@ApiResponse(responseCode = "400", description = "Solicitud erronea", content = @Content(schema = @Schema(implementation = ErrorObject.class))) })
	@GetMapping("{nombre}")
	public String obtenerProfesorPorNombre(@PathVariable("nombre") final String nombre) throws JsonProcessingException {
		return objectMapper.writeValueAsString(this.profesorService.obtenerProfesores().stream()
				.filter(x -> x.getNombre().startsWith(nombre.toUpperCase())).collect(Collectors.toList()));

	}

	@Operation(summary = "Crear profesor", description = "Esta operacion crea un nuevo profesor y lo inserta en la base de datos", tags = "Profesor")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se ha ejecutado la consulta correctamente", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = Profesor.class))) }),
			@ApiResponse(responseCode = "500", description = "No se ha podido crear el profesor porque se añadieron atributos que no estan creados en la base de datos", content = @Content),
			@ApiResponse(responseCode = "404", description = "No se ha encontrado el profesor con ese id", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject.class))) }),
			@ApiResponse(responseCode = "400", description = "Solicitud erronea", content = @Content(schema = @Schema(implementation = ErrorObject.class))) })
	@PostMapping(path = "/añadir", consumes = "application/json")
	public ResponseEntity<Profesor> guardarprofesor(@RequestBody Profesor p) {

		if (asignaturaService.existen(p.getAsignaturas())) {

		} else {
			p.setAsignaturas(null);
		}

		if (p.getDepartamento() != null) {

			if (departamentoService.findById(p.getDepartamento().getId()) == null) {
				p.setDepartamento(null);
				this.profesorService.saveId(p);
				return new ResponseEntity<Profesor>(HttpStatus.OK);
			} else {
				this.profesorService.saveId(p);

				return new ResponseEntity<Profesor>(HttpStatus.OK);
			}

		} else {
			this.profesorService.saveId(p);
			return new ResponseEntity<Profesor>(HttpStatus.OK);
		}

	}

	@Operation(summary = "Borrar profesor", description = "Esta operacion permite eliminar un profesor introduciendo como parametro su identificador (id)", tags = "Profesor")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se ha borrado el profesor de la base de datos correctamente", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = Profesor.class))) }),
			
			@ApiResponse(responseCode = "404", description = "No se ha encontrado el profesor con ese id", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject.class))) }),
			@ApiResponse(responseCode = "400", description = "Solicitud erronea", content = @Content(schema = @Schema(implementation = ErrorObject.class))) })

	@DeleteMapping(path = "/eliminar/{id}")
	public ResponseEntity<Profesor> eliminarPorId(@PathVariable("id") Long id) {
		boolean ok = this.profesorService.eliminaProfesorPorId(id);
		if (ok) {
			return new ResponseEntity<Profesor>(HttpStatus.OK);

		} else {
			return new ResponseEntity<Profesor>(HttpStatus.NOT_FOUND);

		}

	}

}
