package de.joshuaschnabel.wem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

@SpringBootApplication
@EnableWebFluxSecurity
@EnableHypermediaSupport(type = {})
public class WEMApplication {

	public static void main(String[] args) {
		SpringApplication.run(WEMApplication.class, args);
	}

}
