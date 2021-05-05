//package com.apiuni.apiuni.controlador;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.apiuni.apiuni.modelo.Alumno;
//import com.apiuni.apiuni.modelo.Departamento;
//import com.apiuni.apiuni.modelo.ErrorObject;
//import com.apiuni.apiuni.servicio.AsignaturaService;
//import com.apiuni.apiuni.servicio.DepartamentoService;
//import com.apiuni.apiuni.servicio.ProfesorService;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.media.ArraySchema;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//
//@RestController
//@RequestMapping("/alumnos")
//public class AlumnoController {
//
//	@Autowired
//	DepartamentoService departamentoService;
//
//	@Autowired
//	ProfesorService profesorService;
//
//	@Autowired
//	AsignaturaService asignaturaService;
//	
//	@Autowired
//    private ObjectMapper objectMapper;
//
//	@Operation(summary = "Obtener alumnos", description = "Esta operacion devuelve todos los alumnos de la pagina web", tags = "Alumno")
//	@ApiResponses(value = {
//			@ApiResponse(responseCode = "200", description = "Se han obtenido todos los resultados de alumnos de la base de datos correctamente", content = {
//					@Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class)),
//
//							mediaType = "application/json") }),
//			@ApiResponse(responseCode = "404", description = "No disponible", content = @Content),
//			@ApiResponse(responseCode = "400", description = "Solicitud erronea", content = @Content(schema = @Schema(implementation = ErrorObject.class))) })
//	@GetMapping()
//	public String obtenerAlumnos() throws JsonProcessingException {
//		
//		
//		return objectMapper.writeValueAsString(this.departamentoService.findAll());
//	}
//
//	@Operation(summary = "Crear alumno", description = "Esta operacion crea un nuevo alumno y lo inserta en la base de datos", tags = "Alumno")
//	@ApiResponses(value = {
//			@ApiResponse(responseCode = "200", description = "Se ha creado el alumno y se ha insertado en la base de datos correctamente", content = {
//					@Content(array = @ArraySchema(schema = @Schema(implementation = Alumno.class))) }),
//			@ApiResponse(responseCode = "500", description = "No se ha podido crear el alumno porque se añadieron atributos que no estan creados en la base de datos", content = @Content),
//
//			@ApiResponse(responseCode = "400", description = "Solicitud erronea", content = @Content(schema = @Schema(implementation = ErrorObject.class))) })
//	@PostMapping(path = "/añadir", consumes = "application/json")
//	public String guardarDepartamento(@RequestBody Departamento d) throws JsonProcessingException {
//
//		boolean asig = d.getAsignaturas().stream().filter(x -> this.asignaturaService.findById(x.getId()) == null)
//				.count() != 0;
//		boolean prof = d.getProfesores().stream()
//				.filter(x -> this.profesorService.obtenerProfesorPorId(x.getId()) == null).count() != 0;
//		
//
//		if (d.getProfesores() != null && d.getAsignaturas() != null) {
//			if (asig && prof) {
//				d.setProfesores(null);
//				d.setAsignaturas(null);
//				Long id = this.departamentoService.saveId(d);
//				
//				return objectMapper.writeValueAsString(this.departamentoService.findById(id));
//
//			}
//
//			else if (asig && !prof) {
//
//				d.setAsignaturas(null);
//				Long id = this.departamentoService.saveId(d);
//				this.profesorService.saveAll(d.getProfesores());
//			
//				
//				return objectMapper.writeValueAsString(this.departamentoService.findById(id));
//
//			} else if (!asig && prof) {
//
//				d.setAsignaturas(null);
//				Long id = this.departamentoService.saveId(d);
//				return objectMapper.writeValueAsString(this.departamentoService.findById(id));
//				
//			} else {
//
//				Long id = this.departamentoService.saveId(d);
//
//				return objectMapper.writeValueAsString(this.departamentoService.findById(id));
//			}
//		} else {
//			Long id = this.departamentoService.saveId(d);
//			
//			return objectMapper.writeValueAsString(this.departamentoService.findById(id));
//	}
//	}
//}
