package de.joshuaschnabel.wim.application.code;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

	public static class InvalidBearerToken extends AuthenticationException {

		private static final long serialVersionUID = 1L;

		public InvalidBearerToken(String msg) {
			super(msg);
		}
	}

	@Autowired
	private CodeService codeService;

	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		return Mono.justOrEmpty(authentication)//
				.filter(auth -> auth.getClass().isAssignableFrom(BearerToken.class))//
				.cast(BearerToken.class) //
				.flatMap(x -> Mono.just(this.validate(x)))
				.onErrorMap(error -> new InvalidBearerToken(error.getMessage()));

	}

	private Authentication validate(BearerToken token) {
		var invitationId = this.codeService.validateToken(token.getUserAgent(), token.getValue());

		if (invitationId.isPresent()) {
			return UsernamePasswordAuthenticationToken.authenticated(invitationId.get(), invitationId.get(),
					List.of(new SimpleGrantedAuthority("ROLE_GUEST")));
		}

		throw new IllegalArgumentException("Token is not valid.");
	}

}
