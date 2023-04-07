package de.joshuaschnabel.wim.application.code;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import de.joshuaschnabel.wim.domain.invitation.InvitationId;

@Service
public class CodeService {

	private String secret = "3dnTwXZmLjTXAKBoVumUzg34jPamcyF2";

	public String generateToken(String userAgent, InvitationId iid) {
		var algorithm = Algorithm.HMAC256(userAgent + this.secret);
		return JWT.create().withIssuer("wim").withClaim("invitationId", iid.get()).sign(algorithm);
	}

	public Optional<InvitationId> validateToken(String userAgent, String token) {
		DecodedJWT decodedJWT;
		try {
			var algorithm = Algorithm.HMAC256(userAgent + this.secret);
			var verifier = JWT.require(algorithm).withIssuer("wim").build();
			decodedJWT = verifier.verify(token);
			return Optional.of(InvitationId.of(decodedJWT.getClaim("invitationId").asString()));
		} catch (JWTVerificationException exception) {
			return Optional.empty();
		}
	}

}
