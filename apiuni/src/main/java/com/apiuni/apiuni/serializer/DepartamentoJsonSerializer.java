package com.apiuni.apiuni.serializer;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.boot.jackson.JsonComponent;

import com.apiuni.apiuni.modelo.Departamento;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@JsonComponent
public class DepartamentoJsonSerializer extends JsonSerializer<Departamento> {

	@Override
	public void serialize(Departamento departamento, JsonGenerator jsonGenerator, SerializerProvider serializers)
			throws IOException {
		
		jsonGenerator.writeStartObject();
		 jsonGenerator.writeStringField(
		          "departamentoId", 
		          departamento.getId().toString());
		 jsonGenerator.writeStringField(
		          "Nombre", 
		          departamento.getNombre());
		 jsonGenerator.writeStringField(
		          "Sede", 
		          departamento.getSede());
		 jsonGenerator.writeStringField(
		          "Email", 
		          departamento.getEmail());
		 jsonGenerator.writeStringField(
		          "Teléfono", 
		          departamento.getTelefono());
		 jsonGenerator.writeStringField(
		          "Web", 
		          departamento.getWeb());
		 if(departamento.getAsignaturas()== null) {
			 jsonGenerator.writeStringField(
			          "Asignaturas", 
			          null);
		 }else {
		 jsonGenerator.writeStringField(
		          "Asignaturas", 
		          departamento.getAsignaturas().stream().map(x->x.getNombre()).collect(Collectors.toSet()).toString());}
		 
		 if(departamento.getProfesores()==null) {
			 jsonGenerator.writeStringField(
			          "Profesores", 
			          null);
		 }else {
		 jsonGenerator.writeStringField(
		          "Profesores", 
		          departamento.getProfesores().stream().map(x->x.getNombre()).collect(Collectors.toSet()).toString());}
		 
		 
		 jsonGenerator.writeEndObject();
		
	}

}
