package com.apiuni.apiuni.serializer;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.boot.jackson.JsonComponent;

import com.apiuni.apiuni.modelo.Alumno;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@JsonComponent
public class AlumnoJsonSerializer extends JsonSerializer<Alumno> {

	@Override
	public void serialize(Alumno alumno, JsonGenerator jsonGenerator, SerializerProvider serializers)
			throws IOException {

		jsonGenerator.writeStartObject();

		jsonGenerator.writeStringField("alumnoId", alumno.getId().toString());
		jsonGenerator.writeStringField("email", alumno.getEmail().toString());
		jsonGenerator.writeStringField("apellidos", alumno.getApellidos().toString());
		jsonGenerator.writeStringField("nombre", alumno.getNombre().toString());
		jsonGenerator.writeStringField("telefono", alumno.getTelefono().toString());
		jsonGenerator.writeStringField("edad", alumno.getEdad().toString());

		

		jsonGenerator.writeEndObject();

	}

}
