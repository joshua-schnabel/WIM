package de.joshuaschnabel.wim.application.code;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class JwtServerAuthenticationConverter implements ServerAuthenticationConverter {

	@Override
	public Mono<Authentication> convert(ServerWebExchange exchange) {
		var userAgent = exchange.getRequest().getHeaders().getFirst(HttpHeaders.USER_AGENT);
		return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
				.filter(it -> it.startsWith("Bearer ")).map(it -> it.substring(7))
				.map(x -> new BearerToken(x, userAgent));
	}

}
