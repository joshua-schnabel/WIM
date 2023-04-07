package de.joshuaschnabel.wim.infrastructur.presentation.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.ingogriebsch.spring.hateoas.siren.SirenConfiguration;
import de.ingogriebsch.spring.hateoas.siren.SirenMediaTypeConfiguration;

@Configuration
@Import({ SirenMediaTypeConfiguration.class })
public class SirenCustomConfiguration {
	@Bean
	public ObjectMapper objectMapper() {
		final var objectMapper = new ObjectMapper();
		objectMapper.registerModule(new HttpMethodSerializerModule());
		objectMapper.enable(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY);
		objectMapper.enable(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS);
		return objectMapper;
	}

	@Bean
	public SirenConfiguration sirenConfiguration() {
		return new SirenConfiguration().withEntityAndCollectionModelSubclassingEnabled(true);
	}
}
