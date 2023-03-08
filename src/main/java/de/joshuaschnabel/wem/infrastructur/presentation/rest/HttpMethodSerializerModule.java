package de.joshuaschnabel.wem.infrastructur.presentation.rest;

import org.springframework.http.HttpMethod;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class HttpMethodSerializerModule extends SimpleModule {

	/**
     *
     */
    private static final long serialVersionUID = 4379897434604575039L;

    public HttpMethodSerializerModule() {
		this.addSerializer(HttpMethod.class, new HttpMethodSerializer());
	}
}
