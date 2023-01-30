package de.joshuaschnabel.wem.infrastructur.presentation.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.ingogriebsch.spring.hateoas.siren.SirenMediaTypeConfiguration;

@Configuration
@Import({SirenMediaTypeConfiguration.class})
public class SirenCustomConfiguration {
	@Bean
	public ObjectMapper objectMapper() {
		final var objectMapper = new ObjectMapper();
		objectMapper.registerModule(new HttpMethodSerializerModule());
		return objectMapper;
	}
}
