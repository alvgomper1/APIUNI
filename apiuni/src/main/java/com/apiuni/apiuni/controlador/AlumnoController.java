package com.apiuni.apiuni.controlador;

import java.util.ArrayList;

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

import com.apiuni.apiuni.modelo.Alumno;
import com.apiuni.apiuni.modelo.AlumnoRequest;
import com.apiuni.apiuni.modelo.Asignatura;
import com.apiuni.apiuni.modelo.ErrorObject400;
import com.apiuni.apiuni.modelo.ErrorObject404;
import com.apiuni.apiuni.modelo.ErrorObject409;
import com.apiuni.apiuni.servicio.AlumnoService;
import com.apiuni.apiuni.servicio.AsignaturaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {

	@Autowired
	AlumnoService alumnoService;

	@Autowired
	AsignaturaService asignaturaService;

	@Autowired
	private ObjectMapper objectMapper;

	@Operation(summary = "Obtener alumnos", description = "Esta operacion devuelve todos los alumnos de la pagina web", tags = "Alumno")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se han obtenido todos los resultados de alumnos de la base de datos correctamente", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = "No disponible", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject404.class)), mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Solicitud erronea", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject400.class)), mediaType = "application/json")) })
	@GetMapping()
	public String obtenerAlumnos() throws JsonProcessingException {

		return objectMapper.writeValueAsString(this.alumnoService.findAllAlumno());
	}

	@Operation(summary = "Crear alumno", description = "Se ha ejecutado la consulta correctamente", tags = "Alumno")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se ha creado el alumno y se ha insertado en la base de datos correctamente", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)),mediaType = "") }),
			@ApiResponse(responseCode = "500", description = "No se ha podido crear el alumno porque el formato introducido es incorrecto", content = @Content()),
			@ApiResponse(responseCode = "404", description = "No se ha encontrado el alumno con ese id", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject404.class)), mediaType = "application/json") }),
			@ApiResponse(responseCode = "409", description = "Ya existe un alumno con ese id que se muestra en la respuesta", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject409.class)), mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Solicitud erronea", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject400.class)), mediaType = "application/json")) })
	@PostMapping(path = "/añadir", consumes = "application/json")
	public ResponseEntity<Alumno> guardarAlumno(@RequestBody AlumnoRequest a) {
		
		if (alumnoService.findAlumnoById(a.getId()) != null) {
			Alumno alum = this.alumnoService.findAlumnoById(a.getId());
			return new ResponseEntity<Alumno>(alum,HttpStatus.CONFLICT);
		}

		else {
			Alumno alumno = new Alumno();
			alumno.setAsignaturas(new ArrayList<Asignatura>());
			alumno.setApellidos(a.getApellidos());
			
			alumno.setEdad(a.getEdad());
			alumno.setEmail(a.getEmail());
			alumno.setId(a.getId());
			alumno.setNombre(a.getNombre());
			alumno.setTelefono(a.getTelefono());
			
			this.alumnoService.saveId(alumno);

			return new ResponseEntity<Alumno>(alumno,HttpStatus.OK);
		}

	}

	@Operation(summary = "Borrar alumno", description = "Esta operacion permite eliminar un alumno introduciendo como parametro su identificador (id)", tags = "Alumno")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se ha borrado el alumno que se muestra en la respuesta de la base de datos correctamente", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class))) }),
			@ApiResponse(responseCode = "404", description = "No se ha encontrado el alumno con ese id", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject404.class)), mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Solicitud erronea", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject400.class)), mediaType = "application/json")) })

	@DeleteMapping(path = "/eliminar/{id}")
	public ResponseEntity<Alumno> eliminarPorId(@PathVariable("id") Long id) {
		boolean ok = this.alumnoService.eliminaAlumnoPorId(id);
		if (ok) {
			return new ResponseEntity<Alumno>(this.alumnoService.findAlumnoById(id),HttpStatus.OK);

		} else {

			return new ResponseEntity<Alumno>(HttpStatus.NOT_FOUND);
		}

	}

	@Operation(summary = "Editar alumno", description = "Esta operacion edita un alumno y lo guarda en la base de datos", tags = "Alumno")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se ha ejecutado la consulta correctamente", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class))) }),

			@ApiResponse(responseCode = "404", description = "No se ha encontrado el alumno con ese id", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject404.class)), mediaType = "application/json") }),

			@ApiResponse(responseCode = "500", description = "No se ha podido editar el alumno porque los datos introducidos son incorrectos", content = @Content),

			@ApiResponse(responseCode = "400", description = "Solicitud erronea", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject400.class)), mediaType = "application/json")) })

	@PutMapping("/{id}")
	public ResponseEntity<Alumno> actualizaAlumno(@PathVariable("id") final long id, @RequestBody final AlumnoRequest a) {

		Alumno alumno = alumnoService.findAlumnoById(id);
		if (alumno == null) {
			return new ResponseEntity<Alumno>(HttpStatus.NOT_FOUND);

		} else {
			alumno.setNombre(a.getNombre());
			alumno.setApellidos(a.getApellidos());
			alumno.setEdad(a.getEdad());
			alumno.setEmail(a.getEmail());
			alumno.setTelefono(a.getTelefono());

			alumnoService.save(alumno);
			return new ResponseEntity<Alumno>(alumno,HttpStatus.OK);

		}

	}
}
