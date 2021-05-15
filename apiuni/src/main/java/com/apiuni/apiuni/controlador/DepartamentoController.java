package com.apiuni.apiuni.controlador;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apiuni.apiuni.modelo.Asignatura;
import com.apiuni.apiuni.modelo.Departamento;
import com.apiuni.apiuni.modelo.ErrorObject;
import com.apiuni.apiuni.modelo.Profesor;
import com.apiuni.apiuni.servicio.AsignaturaService;
import com.apiuni.apiuni.servicio.DepartamentoService;
import com.apiuni.apiuni.servicio.ProfesorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/departamentos")
public class DepartamentoController {

	@Autowired
	DepartamentoService departamentoService;

	@Autowired
	ProfesorService profesorService;

	@Autowired
	AsignaturaService asignaturaService;
	
	@Autowired
    private ObjectMapper objectMapper;

	@Operation(summary = "Obtener departamentos", description = "Esta operacion devuelve todos los departamentos de la pagina web", tags = "Departamento")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se han obtenido todos los resultados de departamentos de la base de datos correctamente", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = Departamento.class)),

							mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = "No disponible", content = @Content),
			@ApiResponse(responseCode = "400", description = "Solicitud erronea", content = @Content(schema = @Schema(implementation = ErrorObject.class))) })
	@GetMapping()
	public String obtenerDepartamentos() throws JsonProcessingException {
		
		
		return objectMapper.writeValueAsString(this.departamentoService.findAll());
	}

	@Operation(summary = "Crear Departamento", description = "Esta operacion crea un nuevo Departamento y lo inserta en la base de datos", tags = "Departamento")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se ha creado el Departamento y se ha insertado en la base de datos correctamente", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = Departamento.class))) }),
			@ApiResponse(responseCode = "500", description = "No se ha podido crear el Departamento porque se añadieron atributos que no estan creados en la base de datos", content = @Content),

			@ApiResponse(responseCode = "400", description = "Solicitud erronea", content = @Content(schema = @Schema(implementation = ErrorObject.class))) })
	@PostMapping(path = "/añadir", consumes = "application/json")
	public String guardarDepartamento(@RequestBody Departamento d) throws JsonProcessingException {

		boolean asig = d.getAsignaturas().stream().filter(x -> this.asignaturaService.findById(x.getId()) == null)
				.count() != 0;
		boolean prof = d.getProfesores().stream()
				.filter(x -> this.profesorService.obtenerProfesorPorId(x.getId()) == null).count() != 0;
		

		if (d.getProfesores() != null && d.getAsignaturas() != null) {
			if (asig && prof) {
				d.setProfesores(null);
				d.setAsignaturas(null);
				Long id = this.departamentoService.saveId(d);
				
				return objectMapper.writeValueAsString(this.departamentoService.findById(id));

			}

			else if (asig && !prof) {

				d.setAsignaturas(null);
				Long id = this.departamentoService.saveId(d);
				this.profesorService.saveAll(d.getProfesores());
			
				
				return objectMapper.writeValueAsString(this.departamentoService.findById(id));

			} else if (!asig && prof) {

				d.setAsignaturas(null);
				Long id = this.departamentoService.saveId(d);
				return objectMapper.writeValueAsString(this.departamentoService.findById(id));
				
			} else {

				Long id = this.departamentoService.saveId(d);

				return objectMapper.writeValueAsString(this.departamentoService.findById(id));
			}
		} else {
			Long id = this.departamentoService.saveId(d);
			
			return objectMapper.writeValueAsString(this.departamentoService.findById(id));
	}
	}
	
	@Operation(summary = "Borrar Departamento", description = "Esta operacion permite eliminar una departamento introduciendo como parametro su identificador (id)", tags = "Departamento")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se ha borrado el departamento de la base de datos correctamente", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = Departamento.class))) }),
			@ApiResponse(responseCode = "404", description = "No disponible", content = @Content),
			@ApiResponse(responseCode = "400", description = "Solicitud erronea", content = @Content(schema = @Schema(implementation = ErrorObject.class))) })

	@DeleteMapping(path = "/eliminar/{id}")
	public String eliminarPorId(@PathVariable("id") Long id) {
		
		if(this.departamentoService.findById(id)==null) {
			return "No existe una departamento con el id " + id;
			
		}else {

		Set<Profesor> profesores = this.departamentoService.findById(id).getProfesores();
		profesores.stream().forEach(x->x.setDepartamento(null));
		this.profesorService.saveAll(profesores);
		boolean ok = this.departamentoService.eliminaDepartamentoPorId(id);
		if (ok) {
			return "Se eliminó la departamento con id " + id + "junto con todos las asignaturas que pertenecían a dicho departamento";
		} else {
			return "No pudo eliminar la departamento con id " + id;
		}
		}
	}
	
}
