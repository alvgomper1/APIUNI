package com.apiuni.apiuni.controlador;

import java.util.Set;

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
import com.apiuni.apiuni.modelo.Departamento;
import com.apiuni.apiuni.modelo.DepartamentoRequest;
import com.apiuni.apiuni.modelo.ErrorObject400;
import com.apiuni.apiuni.modelo.ErrorObject404;
import com.apiuni.apiuni.modelo.ErrorObject409;
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
			@ApiResponse(responseCode = "404", description = "No disponible", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject404.class)), mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Solicitud erronea", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject400.class)), mediaType = "application/json")) })
	@GetMapping()
	public String obtenerDepartamentos() throws JsonProcessingException {

		return objectMapper.writeValueAsString(this.departamentoService.findAll());
	}

	@Operation(summary = "Crear Departamento", description = "Esta operacion crea un nuevo Departamento y lo inserta en la base de datos", tags = "Departamento")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se ha creado el Departamento y se ha insertado en la base de datos correctamente", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = DepartamentoRequest.class)),mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", description = "No se ha podido crear el Departamento porque se añadieron atributos que no estan creados en la base de datos", content = @Content),
			@ApiResponse(responseCode = "404", description = "No se ha encontrado el departamento con ese id", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject404.class)), mediaType = "application/json") }),
			@ApiResponse(responseCode = "409", description = "Ya existe un departamento con ese id, el cual se muestra en la llamada", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject409.class)), mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Solicitud erronea", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject400.class)), mediaType = "application/json")) })
	@PostMapping(path = "/añadir", consumes = "application/json")
	public ResponseEntity<Departamento> guardarDepartamento(@RequestBody DepartamentoRequest d)
			throws JsonProcessingException {

		
		if(this.departamentoService.findById(d.getId())!=null) {
			Departamento dep = this.departamentoService.findById(d.getId());
			return new ResponseEntity<Departamento>(dep,HttpStatus.CONFLICT);
		
		}else {
			
			Departamento departamento = new Departamento();
			departamento.setAsignaturas(null);
			departamento.setEmail(d.getEmail());
			departamento.setId(d.getId());
			departamento.setNombre(d.getNombre());
			departamento.setProfesores(null);
			departamento.setSede(d.getSede());
			departamento.setTelefono(d.getTelefono());
			departamento.setWeb(d.getWeb());
			this.departamentoService.save(departamento);

			return new ResponseEntity<Departamento>(departamento,HttpStatus.OK);
		}
		
	}

	@Operation(summary = "Borrar Departamento", description = "Esta operacion permite eliminar una departamento introduciendo como parametro su identificador (id)", tags = "Departamento")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se ha borrado el departamento que se muestra en la llamada de la base de datos correctamente", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = Departamento.class))) }),
			@ApiResponse(responseCode = "404", description = "No se ha encontrado el departamento con ese id", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject404.class)), mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Solicitud erronea", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject404.class)), mediaType = "application/json")) })

	@DeleteMapping(path = "/eliminar/{id}")
	public ResponseEntity<Departamento> eliminarPorId(@PathVariable("id") Long id) {

		if (this.departamentoService.findById(id) == null) {
			return new ResponseEntity<Departamento>(HttpStatus.NOT_FOUND);

		} else {

			Departamento d= this.departamentoService.findById(id);
			Set<Profesor> profesores = this.departamentoService.findById(id).getProfesores();
			profesores.stream().forEach(x -> x.setDepartamento(null));
			this.profesorService.saveAll(profesores);
			boolean ok = this.departamentoService.eliminaDepartamentoPorId(id);
			if (ok) {
				return new ResponseEntity<Departamento>(d,HttpStatus.OK);
			} else {
				return new ResponseEntity<Departamento>(HttpStatus.NOT_FOUND);
			}
		}
	}
	
	
	@Operation(summary = "Añadir profesor al departamento", description = "Esta operación  añade un profesor al departamento", tags = "Departamento")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se ha ejecutado la consulta correctamente", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = Departamento.class)), mediaType = "application/json") }),
			
			@ApiResponse(responseCode = "409", description = "El profesor ya tiene otro departamento", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject409.class)), mediaType = "application/json") }),
			
			@ApiResponse(responseCode = "500", description = "No se ha podido añadir el profesor", content = @Content),
			@ApiResponse(responseCode = "404", description = "No se ha encontrado el departamento o el profesor con ese id", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject404.class)),	mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Solicitud errónea", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject404.class)),	mediaType = "application/json")) })
	@PutMapping("{idDepartamento}/añadirProfesor/{idProfesor}")
	public ResponseEntity<Departamento> anadeProfesorPorId(@PathVariable("idDepartamento") final long idDepartamento,
			@PathVariable("idProfesor") final long idProfesor)   {

		Departamento d = departamentoService.findById(idDepartamento);
		Profesor p = profesorService.obtenerProfesorPorId(idProfesor);
		if (d == null || p == null) {
			return new ResponseEntity<Departamento>(HttpStatus.NOT_FOUND);
		} else {
			
			if (p.getDepartamento()!=null) {
				//El profesor ya tiene departamento
				return new ResponseEntity<Departamento>(HttpStatus.CONFLICT);
			}
			else {
			d.getProfesores().add(p);
			p.setDepartamento(d);
			
			departamentoService.save(d);
			profesorService.saveId(p);
			return new ResponseEntity<Departamento>(d,HttpStatus.OK);	
			}
			
		}

	}
	
	@Operation(summary = "Añadir asignatura al departamento", description = "Esta operación  añade una asignatura al departamento", tags = "Departamento")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se ha ejecutado la consulta correctamente", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = Departamento.class)), mediaType = "application/json") }),
			
			@ApiResponse(responseCode = "409", description = "La asignatura ya tiene otro departamento", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject409.class)), mediaType = "application/json") }),
			
			@ApiResponse(responseCode = "500", description = "No se ha podido añadir la asignatura", content = @Content),
			@ApiResponse(responseCode = "404", description = "No se ha encontrado el departamento o la asignatura con ese id", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject404.class)),	mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Solicitud errónea", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject404.class)),	mediaType = "application/json")) })
	@PutMapping("{idDepartamento}/añadirAsignatura/{idAsignatura}")
	public ResponseEntity<Departamento> anadeAsignaturaPorId(@PathVariable("idAsignatura") final long idAsignatura,
			@PathVariable("idDepartamento") final long idDepartamento)   {

		Departamento d = departamentoService.findById(idDepartamento);
		Asignatura a = asignaturaService.findById(idAsignatura);
				
		if (d == null || a == null) {
			return new ResponseEntity<Departamento>(HttpStatus.NOT_FOUND);
		} else {
			
			if (a.getDepartamento()!=null) {
				//La asignatura ya tiene departamento
				return new ResponseEntity<Departamento>(departamentoService.findById(idDepartamento),HttpStatus.CONFLICT);
			}
			else {
			d.getAsignaturas().add(a);
			a.setDepartamento(d);
			
			departamentoService.save(d);
			asignaturaService.saveId(a);
			return new ResponseEntity<Departamento>(d,HttpStatus.OK);	
			}
			
		}

	}

}
