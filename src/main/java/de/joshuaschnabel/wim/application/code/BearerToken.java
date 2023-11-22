package de.joshuaschnabel.wim.application.code;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

import lombok.Getter;

public class BearerToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = -576154103985843063L;

	@Getter
	private String value;
	@Getter
	private String userAgent;

	public BearerToken(String value, String userAgent) {
		super(AuthorityUtils.NO_AUTHORITIES);
		this.value = value;
		this.userAgent = userAgent;
	}

	@Override
	public Object getCredentials() {
		return this.value;
	}

	@Override
	public Object getPrincipal() {
		return this.value;
	}

}
