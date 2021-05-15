package com.apiuni.apiuni.controlador;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.apiuni.apiuni.modelo.Departamento;
import com.apiuni.apiuni.modelo.ErrorObject;
import com.apiuni.apiuni.modelo.Profesor;
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

	@Operation(summary = "Obtener asignaturas", description = "Esta operacion devuelve todas los asignaturas de la pagina web", tags = "Asignatura")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se han obtenido todos los resultados de asignaturas de la base de datos correctamente", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = Asignatura.class)),

							mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = "No disponible", content = @Content),
			@ApiResponse(responseCode = "400", description = "Solicitud erronea", content = @Content(schema = @Schema(implementation = ErrorObject.class))) })
	@GetMapping()
	public String obtenerAsignaturas() throws JsonProcessingException {

		return objectMapper.writeValueAsString(this.asignaturaService.findAllAsignaturas());
	}

	@Operation(summary = "Crear Asignatura", description = "Esta operacion crea una nueva Asignatura y lo inserta en la base de datos", tags = "Asignatura")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se ha ejecutado la consulta correctamente", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = Asignatura.class))) }),
			@ApiResponse(responseCode = "500", description = "No se ha podido crear la Asignatura porque se añadieron atributos que no estan creados en la base de datos", content = @Content),

			@ApiResponse(responseCode = "400", description = "Solicitud erronea", content = @Content(schema = @Schema(implementation = ErrorObject.class))) })
	@PostMapping(path = "/añadir", consumes = "application/json")
	public String guardarAsignatura(@RequestBody Asignatura d) throws JsonProcessingException {

		boolean prof = d.getProfesores().stream()
				.filter(x -> this.profesorService.obtenerProfesorPorId(x.getId()) == null).count() != 0;
		boolean titulacion = this.titulacionService.findById(d.getTitulacion().getId()) == null;

		boolean departamento = this.titulacionService.findById(d.getDepartamento().getId()) == null;

		boolean alumnos = d.getAlumnos().stream().filter(x -> this.alumnosService.findAlumnoById(x.getId()) == null)
				.count() != 0;

		if (d.getProfesores() != null && d.getTitulacion() != null && d.getDepartamento() != null
				&& d.getAlumnos() != null) {

			if (prof) {
				d.setProfesores(null);
			}
			if (titulacion) {
				d.setTitulacion(null);
			}
			if (departamento) {
				d.setDepartamento(null);
			}
			if (alumnos) {
				d.setAlumnos(null);
			}
			Long id = this.asignaturaService.saveId(d);

			return objectMapper.writeValueAsString(this.asignaturaService.findById(id));

		} else {
			Long id = this.asignaturaService.saveId(d);

			return objectMapper.writeValueAsString(this.asignaturaService.findById(id));
		}
	}
	
	@Operation(summary = "Borrar Asignatura", description = "Esta operacion permite eliminar una asignatura introduciendo como parametro su identificador (id)", tags = "Asignatura")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se ha borrado la asignatura de la base de datos correctamente", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = Asignatura.class))) }),
			@ApiResponse(responseCode = "404", description = "No disponible", content = @Content),
			@ApiResponse(responseCode = "400", description = "Solicitud erronea", content = @Content(schema = @Schema(implementation = ErrorObject.class))) })

	@DeleteMapping(path = "/eliminar/{id}")
	public String eliminarPorId(@PathVariable("id") Long id) {
		
		if(this.asignaturaService.findById(id)==null) {
			return "No existe una asignatura con el id " + id;
			
		}else {
		
		boolean ok = this.asignaturaService.eliminaAsignaturaPorId(id);
		if (ok) {
			return "Se eliminó la asignatura con id " + id;
		} else {
			return "No pudo eliminar la asignatura con id " + id;
		}
		}
	}

	@Operation(summary = "Añadir alumno nuevo a la asignatura", description = "Esta operacion crea un nuevo alumno y lo añade a la asignatura", tags = "Asignatura")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se ha ejecutado la consulta correctamente", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = Asignatura.class))) }),
			@ApiResponse(responseCode = "500", description = "No se ha podido añadir el alumno", content = @Content),

			@ApiResponse(responseCode = "400", description = "Solicitud erronea", content = @Content(schema = @Schema(implementation = ErrorObject.class))) })
	@PutMapping("{id}/añadirAlumno")
	public String anadeAlumnoAsignatura(@PathVariable("id") final long id, @RequestBody final Alumno a)
			throws JsonProcessingException {

		Asignatura asig = asignaturaService.findById(id);
		if (asig == null) {
			return "No se ha encontrado la asignatura con ese id en la base de datos";
		} else {
			asig.getAlumnos().add(a);

			alumnosService.save(a);
			asignaturaService.saveId(asig);
			return "Se ha añadido el siguiente alumno a la asignatura: \n" + objectMapper.writeValueAsString(a);
		}

	}

	@Operation(summary = "Añadir alumno existente a la asignatura", description = "Esta operacion  añade un alumno a la asignatura", tags = "Asignatura")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se ha ejecutado la consulta correctamente", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = Asignatura.class))) }),
			@ApiResponse(responseCode = "500", description = "No se ha podido añadir el alumno", content = @Content),

			@ApiResponse(responseCode = "400", description = "Solicitud erronea", content = @Content(schema = @Schema(implementation = ErrorObject.class))) })
	@PutMapping("{id}/añadirAlumno/{idAlumno}")
	public String anadeAlumnoAsignaturaPorId(@PathVariable("id") final long id,
			@PathVariable("idAlumno") final long idAlumno) throws JsonProcessingException {

		Asignatura asig = asignaturaService.findById(id);
		Alumno alum = alumnosService.findAlumnoById(idAlumno);
		if (asig == null) {
			return "No se ha encontrado la asignatura con ese id en la base de datos";
		} else {
			if (alum == null) {
				return "No se ha encontrado el alumno con ese id en la base de datos";
			} else {

			}

			asig.getAlumnos().add(alum);

			asignaturaService.saveId(asig);
			return "Se ha añadido el siguiente alumno a la asignatura: \n" + objectMapper.writeValueAsString(alum);
		}

	}

}
