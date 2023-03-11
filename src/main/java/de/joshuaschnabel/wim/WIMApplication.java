package de.joshuaschnabel.wim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

@SpringBootApplication
@EnableWebFluxSecurity
@EnableHypermediaSupport(type = {})
public class WIMApplication {

	public static void main(String[] args) {
		SpringApplication.run(WIMApplication.class, args);
	}

}
