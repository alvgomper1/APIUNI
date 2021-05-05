package com.apiuni.apiuni.controlador;

 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apiuni.apiuni.modelo.ErrorObject;
import com.apiuni.apiuni.modelo.Titulacion;
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

	@Operation(summary = "Obtener titulaciones", description = "Esta operacion devuelve todas las titulaciones de la pagina web", tags = "Titulacion")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se han obtenido todos los resultados de titulaciones de la base de datos correctamente", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = Titulacion.class)),

							mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = "No disponible", content = @Content),
			@ApiResponse(responseCode = "400", description = "Solicitud erronea", content = @Content(schema = @Schema(implementation = ErrorObject.class))) })
	@GetMapping()
	public String obtenerTitulaciones() throws JsonProcessingException {

		return objectMapper.writeValueAsString(this.titulacionService.findAll());
	}

	@Operation(summary = "Crear Titulacion", description = "Esta operacion crea una nueva Titulacion y la inserta en la base de datos", tags = "Titulacion")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se ha creado la Titulacion y se ha insertado en la base de datos correctamente", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = Titulacion.class))) }),
			@ApiResponse(responseCode = "500", description = "No se ha podido crear la Titulacion porque se añadieron atributos que no estan creados en la base de datos", content = @Content),

			@ApiResponse(responseCode = "400", description = "Solicitud erronea", content = @Content(schema = @Schema(implementation = ErrorObject.class))) })
	@PostMapping(path = "/añadir", consumes = "application/json")
	public String guardarTitulacion(@RequestBody Titulacion t) throws JsonProcessingException {

		if (t.getAsignaturas() != null) {

			if (asignaturaService.existen(t.getAsignaturas())) {

				t.setAsignaturas(t.getAsignaturas());

				long id = this.titulacionService.saveId(t);
				return "No se ha encontrado el departamento, se ha creado el profesor con Departamento = null. Resultado: "
						+ objectMapper.writeValueAsString(this.titulacionService.findById(id));
			} else {
				t.setAsignaturas(null);
				long id = this.titulacionService.saveId(t);				 
				return "No se han encontrado las asignaturas, la titulacion se ha guardado sin ninguna. Resultado: "
						+ objectMapper.writeValueAsString(this.titulacionService.findById(id));
			}

		}

		else {
			long id = this.titulacionService.saveId(t);
			return "Se ha añadido correctamente la titulacion " + ". Resultado: "
					+ objectMapper.writeValueAsString(this.titulacionService.findById(id));

		}

		 
	}

}
