package de.joshuaschnabel.wem.infrastructur.presentation.rest.error;

import java.net.URI;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalErrorWebExceptionHandler {

	@ExceptionHandler(HttpException.class)
	public Mono<ResponseEntity<Problem>> handleNotFoundException(
			HttpException ex, ServerWebExchange exchange) {
		final var rsa = ex.getClass().getAnnotation(ResponseStatus.class);
		final var code = Optional.ofNullable(rsa.code())
				.orElse(HttpStatus.INTERNAL_SERVER_ERROR);
		exchange.getRequest().getURI();

		final var problem = Problem.create().withType(URI.create("test"))
				.withTitle("titel").withDetail("detail")
				.withProperties(Map.of("attributes", ex.getAttributes()))
				.withInstance(
						URI.create("urn:uuid:" + UUID.randomUUID().toString()));
		return Mono.just(new ResponseEntity<Problem>(problem, code));
	}
}