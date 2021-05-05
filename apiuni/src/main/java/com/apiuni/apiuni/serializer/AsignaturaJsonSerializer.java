package com.apiuni.apiuni.serializer;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.boot.jackson.JsonComponent;

import com.apiuni.apiuni.modelo.Asignatura;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@JsonComponent
public class AsignaturaJsonSerializer extends JsonSerializer<Asignatura> {

	@Override
	public void serialize(Asignatura asignatura, JsonGenerator jsonGenerator, SerializerProvider serializers)
			throws IOException {

		jsonGenerator.writeStartObject();

		jsonGenerator.writeStringField("asignaturaId", asignatura.getId().toString());

		jsonGenerator.writeStringField("anyo", asignatura.getAno().toString());

		jsonGenerator.writeStringField("caracter", asignatura.getCaracter().toString());

		jsonGenerator.writeStringField("creditos", asignatura.getCreditos().toString());

		jsonGenerator.writeStringField("duracion", asignatura.getDuracion().toString());
		
		jsonGenerator.writeStringField("nombre", asignatura.getNombre().toString());


		if (asignatura.getAlumnos() == null) {
			jsonGenerator.writeStringField("Alumnos", null);
		} else {
			jsonGenerator.writeStringField("Alumnos",
					asignatura.getAlumnos().stream().map(x -> x.getNombre()).collect(Collectors.toSet()).toString());
		}
		
		if (asignatura.getDepartamento() == null) {
			jsonGenerator.writeStringField("Departamento", null);
		} else {
			jsonGenerator.writeStringField("Departamento",
					asignatura.getDepartamento().getNombre());
		}

		if (asignatura.getProfesores() == null) {
			jsonGenerator.writeStringField("Profesores", null);
		} else {
			jsonGenerator.writeStringField("Profesores",
					asignatura.getProfesores().stream().map(x -> x.getNombre()).collect(Collectors.toSet()).toString());
		}
		
		if (asignatura.getTitulacion() == null) {
			jsonGenerator.writeStringField("Titulacion", null);
		} else {
			jsonGenerator.writeStringField("Titulacion",
					asignatura.getTitulacion().getNombre() );
		}
		
		jsonGenerator.writeEndObject();

	}

}
