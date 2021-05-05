package com.apiuni.apiuni.serializer;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.boot.jackson.JsonComponent;

import com.apiuni.apiuni.modelo.Profesor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@JsonComponent
public class ProfesorJsonSerializer extends JsonSerializer<Profesor> {

	@Override
	public void serialize(Profesor profesor, JsonGenerator jsonGenerator, SerializerProvider serializers)
			throws IOException {

		jsonGenerator.writeStartObject();

		jsonGenerator.writeStringField("profesorId", profesor.getId().toString());

		jsonGenerator.writeStringField("email", profesor.getEmail().toString());

		jsonGenerator.writeStringField("nombre", profesor.getNombre().toString());
		jsonGenerator.writeStringField("telefono", profesor.getTelefono().toString());

		if (profesor.getAsignaturas() == null) {
			jsonGenerator.writeStringField("Asignaturas", null);
		} else {
			jsonGenerator.writeStringField("Asignaturas",
					profesor.getAsignaturas().stream().map(x -> x.getNombre()).collect(Collectors.toSet()).toString());
		}

		if (profesor.getDepartamento() == null) {
			jsonGenerator.writeStringField("Departamento", null);
		} else {
			jsonGenerator.writeStringField("Departamento", profesor.getDepartamento().getNombre());
		}

		jsonGenerator.writeEndObject();

	}

}
