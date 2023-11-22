package de.joshuaschnabel.wim;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.CsrfSpec;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.client.web.server.DefaultServerOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;

import de.joshuaschnabel.wim.application.code.JwtAuthenticationManager;
import de.joshuaschnabel.wim.application.code.JwtServerAuthenticationConverter;

@Configuration
@EnableWebFluxSecurity
public class WimWebSecurityConfig {

	@Autowired
	private org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository reactiveClientRegistrationRepository;

	@Autowired
	private JwtAuthenticationManager authManager;

	@Autowired
	private JwtServerAuthenticationConverter converter;

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		var filter = new AuthenticationWebFilter(this.authManager);
		filter.setServerAuthenticationConverter(this.converter);

		// Disable default security.
		http.httpBasic().disable();
		http.csrf(CsrfSpec::disable);
		http.formLogin().disable();
		http.logout().logoutUrl("/api/auth/logout");
		http.authorizeExchange(auth -> {
			auth.pathMatchers("/assets/protected/**").authenticated();
			auth.pathMatchers("/assets/**").permitAll();
			auth.pathMatchers("/api/auth/**").permitAll();
			auth.pathMatchers("/api/**").authenticated();
			auth.pathMatchers("/index.html").permitAll();
			auth.pathMatchers("/favicon.ico").permitAll();
			auth.pathMatchers("/*/*/*").permitAll();
			auth.pathMatchers("/*/*").permitAll();
			auth.pathMatchers("/*").permitAll();
			auth.pathMatchers("/").permitAll();
			auth.anyExchange().authenticated();
		});
		http.oauth2Login(oauth -> {
			oauth.authenticationFailureHandler((webFilterExchange, exception) -> {
				exception.printStackTrace();
				var exchange = webFilterExchange.getExchange();
				exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
				return exchange.getResponse().setComplete();
			});
			oauth.authenticationSuccessHandler(new RedirectServerAuthenticationSuccessHandler("/admin/redirect"));
			var x = new DefaultServerOAuth2AuthorizationRequestResolver(this.reactiveClientRegistrationRepository,
					new PathPatternParserServerWebExchangeMatcher("/api/auth/oauth2/authorization/{registrationId}"));
			oauth.authorizationRequestResolver(x);
			oauth.authenticationMatcher(
					new PathPatternParserServerWebExchangeMatcher("/api/auth/oauth2/code/{registrationId}"));
		});
		http.exceptionHandling(eh -> {
			eh.authenticationEntryPoint(new HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED));
		});
		http.addFilterAt(filter, SecurityWebFiltersOrder.AUTHENTICATION);
		return http.build();
	}

	@Bean
	public GrantedAuthoritiesMapper userAuthoritiesMapper() {
		return authorities -> {
			Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

			authorities.forEach(authority -> {
				if (OidcUserAuthority.class.isInstance(authority)) {
					var oidcUserAuthority = (OidcUserAuthority) authority;

					var idToken = oidcUserAuthority.getIdToken();
					var userInfo = oidcUserAuthority.getUserInfo();

					List<String> groups = idToken.getClaim("groups");
					mappedAuthorities.add(oidcUserAuthority);
					groups.forEach(g -> {
						mappedAuthorities.add(new SimpleGrantedAuthority(g));
					});

				} else if (OAuth2UserAuthority.class.isInstance(authority)) {
					var oauth2UserAuthority = (OAuth2UserAuthority) authority;

					var userAttributes = oauth2UserAuthority.getAttributes();
					// Map the attributes found in userAttributes
					// to one or more GrantedAuthority's and add it to mappedAuthorities

				}
			});

			return mappedAuthorities;
		};
	}
}
