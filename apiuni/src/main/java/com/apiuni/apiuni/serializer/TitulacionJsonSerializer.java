package com.apiuni.apiuni.serializer;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.boot.jackson.JsonComponent;

import com.apiuni.apiuni.modelo.Titulacion;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@JsonComponent
public class TitulacionJsonSerializer extends JsonSerializer<Titulacion> {

	@Override
	public void serialize(Titulacion titulacion, JsonGenerator jsonGenerator, SerializerProvider serializers)
			throws IOException {

		jsonGenerator.writeStartObject();

		jsonGenerator.writeStringField("titulacionId", titulacion.getId().toString());

		jsonGenerator.writeStringField("nombre", titulacion.getNombre().toString());

		if (titulacion.getAsignaturas() == null) {
			jsonGenerator.writeStringField("Asignaturas", null);
		} else {
			jsonGenerator.writeStringField("Asignaturas", titulacion.getAsignaturas().stream().map(x -> x.getNombre())
					.collect(Collectors.toSet()).toString());
		}

		jsonGenerator.writeEndObject();

	}

}
