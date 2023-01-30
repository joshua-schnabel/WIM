package de.joshuaschnabel.wem.infrastructur.presentation.rest;

import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.databind.module.SimpleModule;

public class HttpMethodSerializerModule extends SimpleModule {

	public HttpMethodSerializerModule() {
		this.addSerializer(HttpMethod.class, new HttpMethodSerializer());
	}
}
