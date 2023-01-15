package de.joshuaschnabel.wem.infrastructur.presentation.rest.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/hello")
public class HelloController {

	@GetMapping
	private Mono<String> getAllEmployees() {
		return Mono.just("Hello World !");
	}

}