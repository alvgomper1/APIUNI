package com.apiuni.apiuni.controlador;

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
import com.apiuni.apiuni.modelo.ErrorObject400;
import com.apiuni.apiuni.modelo.ErrorObject404;
import com.apiuni.apiuni.modelo.ErrorObject409;
import com.apiuni.apiuni.modelo.Profesor;
import com.apiuni.apiuni.modelo.Titulacion;
import com.apiuni.apiuni.modelo.TitulacionRequest;
import com.apiuni.apiuni.servicio.AsignaturaService;
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
@RequestMapping("/titulaciones")
public class TitulacionController {

	@Autowired
	TitulacionService titulacionService;

	@Autowired
	AsignaturaService asignaturaService;

	@Autowired
	private ObjectMapper objectMapper;

	@Operation(summary = "Obtener titulaciones", description = "Esta operacion devuelve todas las titulaciones de la pagina web", tags = "Titulación")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se han obtenido todos los resultados de titulaciones de la base de datos correctamente", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = Titulacion.class)),

							mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = "No disponible", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject404.class)), mediaType = "application/json")),

			@ApiResponse(responseCode = "400", description = "Solicitud erronea", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject400.class)), mediaType = "application/json")) })
	@GetMapping()
	public String obtenerTitulaciones() throws JsonProcessingException {

		return objectMapper.writeValueAsString(this.titulacionService.findAll());
	}

	@Operation(summary = "Crear Titulacion", description = "Esta operacion crea una nueva Titulacion y la inserta en la base de datos", tags = "Titulación")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se ha creado la Titulacion y se ha insertado en la base de datos correctamente", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = Titulacion.class))) }),
			@ApiResponse(responseCode = "500", description = "No se ha podido crear la Titulacion porque se añadieron atributos que no estan creados en la base de datos", content = @Content),
			@ApiResponse(responseCode = "404", description = "No se ha encontrado la titulacion con ese id", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject404.class)), mediaType = "application/json") }),
			@ApiResponse(responseCode = "409", description = "Ya existe una titulacion con ese id", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject409.class)), mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Solicitud erronea", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject400.class)), mediaType = "application/json")) })
	@PostMapping(path = "/añadir", consumes = "application/json")
	public ResponseEntity<Titulacion> guardarTitulacion(@RequestBody TitulacionRequest t) {

		
		if(t.getId()<0) {
			return new ResponseEntity<Titulacion>(HttpStatus.BAD_REQUEST);
		}
		
		
		Titulacion res = this.titulacionService.findById(t.getId());
		if(res!= null) {
			
			return new ResponseEntity<Titulacion>(res,HttpStatus.CONFLICT);
		}
		
		else {
			
			Titulacion titulacion = new Titulacion();
			titulacion.setId(t.getId());
			titulacion.setNombre(t.getNombre());
			titulacion.setAsignaturas(null);			
			titulacionService.saveId(titulacion);
			return  new ResponseEntity<Titulacion>(titulacion,HttpStatus.OK);
		}

	}

	@Operation(summary = "Borrar Titulación", description = "Esta operacion permite eliminar una Titulación introduciendo como parametro su identificador (id)", tags = "Titulación")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se ha borrado el Titulación de la base de datos correctamente", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = Titulacion.class))) }),
			@ApiResponse(responseCode = "404", description = "No se ha encontrado la titulacion con ese id", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject404.class)), mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Solicitud erronea", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject400.class)), mediaType = "application/json")) })

	@DeleteMapping(path = "/eliminar/{id}")
	public ResponseEntity<Titulacion> eliminarPorId(@PathVariable("id") Long id) {

		if (this.titulacionService.findById(id) == null) {
			return new ResponseEntity<Titulacion>(HttpStatus.NOT_FOUND);

		} else {

			boolean ok = this.titulacionService.eliminaTitulacionPorId(id);
			if (ok) {
				return new ResponseEntity<Titulacion>(HttpStatus.OK);
			} else {
				return new ResponseEntity<Titulacion>(HttpStatus.NOT_FOUND);
			}
		}
	}
	
	
	
	
	
	
	@Operation(summary = "Añadir asignatura a la titulacion", description = "Esta operación  añade una asignatura la titulacion", tags = "Titulación")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se ha ejecutado la consulta correctamente", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = Titulacion.class)), mediaType = "application/json") }),
			
			@ApiResponse(responseCode = "409", description = "La asignatura ya tiene otra titulacion", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject409.class)), mediaType = "application/json") }),
			
			@ApiResponse(responseCode = "500", description = "No se ha podido añadir la asignatura", content = @Content),
			@ApiResponse(responseCode = "404", description = "No se ha encontrado la titulacion o la asignatura con ese id", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject404.class)),	mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Solicitud errónea", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorObject404.class)),	mediaType = "application/json")) })
	@PutMapping("{idTitulacion}/añadirAsignatura/{idAsignatura}")
	public ResponseEntity<Titulacion> anadeAsignaturaPorId(@PathVariable("idAsignatura") final long idAsignatura,
			@PathVariable("idTitulacion") final long idTitulacion)   {

		Titulacion t = titulacionService.findById(idTitulacion);
		Asignatura a = asignaturaService.findById(idAsignatura);
				
		if (t == null || a == null) {
			return new ResponseEntity<Titulacion>(HttpStatus.NOT_FOUND);
		} else {
			
			if (a.getTitulacion()!=null) {
				//La asignatura ya tiene departamento
				return new ResponseEntity<Titulacion>(titulacionService.findById(idTitulacion),HttpStatus.CONFLICT);
			}
			else {
			t.getAsignaturas().add(a);
			a.setTitulacion(t);
			
			titulacionService.saveId(t);
			asignaturaService.saveId(a);
			return new ResponseEntity<Titulacion>(t,HttpStatus.OK);	
			}
			
		}}

}
