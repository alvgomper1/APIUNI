package com.apiuni.apiuni.controlador;

import java.util.List;
import java.util.Set;

import org.aspectj.weaver.patterns.AbstractSignaturePattern;
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
import com.apiuni.apiuni.modelo.Asignatura;
import com.apiuni.apiuni.modelo.AsignaturaRequest;
import com.apiuni.apiuni.modelo.Departamento;
import com.apiuni.apiuni.modelo.ErrorObject400;
import com.apiuni.apiuni.modelo.ErrorObject404;
import com.apiuni.apiuni.modelo.ErrorObject409;
import com.apiuni.apiuni.modelo.Profesor;
import com.apiuni.apiuni.modelo.Titulacion;
import com.apiuni.apiuni.servicio.AlumnoService;
import com.apiuni.apiuni.servicio.AsignaturaService;
import com.apiuni.apiuni.servicio.DepartamentoService;
import com.apiuni.apiuni.servicio.ProfesorService;
import com.apiuni.apiuni.servicio.TitulacionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/asignaturas")
public class AsignaturaController {

	@Autowired
	DepartamentoService departamentoService;

	@Autowired
	ProfesorService profesorService;

	@Autowired
	TitulacionService titulacionService;

	@Autowired
	AsignaturaService asignaturaService;

	@Autowired
	AlumnoService alumnosService;

	@Autowired
	private ObjectMapper objectMapper;

	@Operation(summary = "Obtener asignaturas", description = "Esta operación devuelve todas los asignaturas de la página web", tags = "Asignatura")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se han obtenido todos los resultados de asignaturas de la base de datos correctamente", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = Asignatura.class)),

							mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = "No disponible", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject404.class)), mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Solicitud errónea", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject400.class)), mediaType = "application/json")) })
	@GetMapping()
	public String obtenerAsignaturas() throws JsonProcessingException {

		return objectMapper.writeValueAsString(this.asignaturaService.findAllAsignaturas());
	}

	@Operation(summary = "Crear Asignatura", description = "Esta operación crea una nueva Asignatura y lo inserta en la base de datos", tags = "Asignatura")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se ha ejecutado la consulta correctamente", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = Asignatura.class))) }),
			@ApiResponse(responseCode = "409", description = "No se puede crear con ese id porque ya existe en la base de datos", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject409.class)), mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", description = "No se ha podido crear la Asignatura porque se añadieron atributos que no están creados en la base de datos", content = @Content),
			@ApiResponse(responseCode = "404", description = "No se ha encontrado la asignatura con ese id", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject404.class)), mediaType = "application/json") }),

			@ApiResponse(responseCode = "400", description = "Solicitud errónea", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject400.class)), mediaType = "application/json")) })
	@PostMapping(path = "/añadir", consumes = "application/json")
	public ResponseEntity<Asignatura> guardarAsignatura(@RequestBody AsignaturaRequest a) {
		
		Asignatura asignatura2 = asignaturaService.findById(a.getId());
		if (asignatura2!=null) {
			return new ResponseEntity<Asignatura>(asignatura2,HttpStatus.CONFLICT);
		}
		
		else {
			Asignatura asignatura = new Asignatura();
			asignatura .setAlumnos(null);
			asignatura.setDepartamento(null);
			asignatura.setProfesores(null);
			asignatura.setTitulacion(null);			
			asignatura.setAno(a.getAno());
			asignatura.setCaracter(a.getCaracter());			
			asignatura.setCreditos(a.getCreditos());			
			asignatura.setDuracion(a.getDuracion());
			asignatura.setId(a.getId());
			asignatura.setNombre(a.getNombre());
			
			asignaturaService.saveId(asignatura);			
			return new ResponseEntity<Asignatura>(asignatura,HttpStatus.OK);
		}
		
		
		
		

	}

	@Operation(summary = "Borrar Asignatura", description = "Esta operación permite eliminar una asignatura introduciendo como parámetro su identificador (id)", tags = "Asignatura")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se ha borrado la asignatura de la base de datos correctamente", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = Asignatura.class))) }),
			@ApiResponse(responseCode = "404", description = "No se ha encontrado la asignatura con ese id", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject404.class)), mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Solicitud errónea", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject400.class)), mediaType = "application/json")) })

	@DeleteMapping(path = "/eliminar/{id}")
	public ResponseEntity<Asignatura> eliminarPorId(@PathVariable("id") Long id) {

		if (this.asignaturaService.findById(id) == null) {
			return new ResponseEntity<Asignatura>(HttpStatus.NOT_FOUND);

		} else {
			
			boolean ok = this.asignaturaService.eliminaAsignaturaPorId(id);
			if (ok) {
				return new ResponseEntity<Asignatura>(HttpStatus.OK);
			} else {
				return new ResponseEntity<Asignatura>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}
	// ----------------------------------------------------------------------------------------------------------------------

	@Operation(summary = "Añadir alumno nuevo a la asignatura", description = "Esta operación crea un nuevo alumno y lo añade a la asignatura", tags = "Asignatura")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se ha ejecutado la consulta correctamente", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = Asignatura.class))) }),
			@ApiResponse(responseCode = "500", description = "No se ha podido añadir el alumno", content = @Content),
			@ApiResponse(responseCode = "404", description = "No se ha encontrado la asignatura con ese id", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject404.class)),	mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Solicitud errónea", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject400.class)),	mediaType = "application/json")) })
	@PutMapping("{id_asignatura}/añadirAlumno")
	public ResponseEntity<Asignatura> anadeAlumnoAsignatura(@PathVariable("id_asignatura") final long id,
			@RequestBody final Alumno a) { // POner aqui AlumnoRequest

		Asignatura asig = asignaturaService.findById(id);
		if (asig == null) {
			return new ResponseEntity<Asignatura>(HttpStatus.NOT_FOUND);
		} else {
			alumnosService.save(a);
			asig.getAlumnos().add(a);
			asignaturaService.saveId(asig);

			return new ResponseEntity<Asignatura>(HttpStatus.OK);
		}

	}  // ----------------------------------------------------------------------------------------------------------------------

	@Operation(summary = "Añadir alumno existente a la asignatura", description = "Esta operación  añade un alumno a la asignatura", tags = "Asignatura")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se ha ejecutado la consulta correctamente", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = Asignatura.class))) }),
			@ApiResponse(responseCode = "500", description = "No se ha podido añadir el alumno", content = @Content),
			@ApiResponse(responseCode = "404", description = "No se ha encontrado la asignatura o el alumno con ese id", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject404.class)),	mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Solicitud errónea", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject404.class)),	mediaType = "application/json")) })
	@PutMapping("{id}/añadirAlumno/{idAlumno}")
	public ResponseEntity<Asignatura> anadeAlumnoAsignaturaPorId(@PathVariable("id") final long id,
			@PathVariable("idAlumno") final long idAlumno)   {

		Asignatura asig = asignaturaService.findById(id);
		Alumno alum = alumnosService.findAlumnoById(idAlumno);
		if (asig == null || alum == null) {
			return new ResponseEntity<Asignatura>(HttpStatus.NOT_FOUND);
		} else {
			   

			asig.getAlumnos().add(alum);

			asignaturaService.saveId(asig);
			return new ResponseEntity<Asignatura>(asig,HttpStatus.OK);
		}

	}

}
