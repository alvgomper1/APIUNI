package com.apiuni.apiuni.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apiuni.apiuni.modelo.Asignatura;
import com.apiuni.apiuni.modelo.Departamento;
import com.apiuni.apiuni.modelo.ErrorObject;
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
			@ApiResponse(responseCode = "200", description = "Se ha creado la Asignatura y se ha insertado en la base de datos correctamente", content = {
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

}
