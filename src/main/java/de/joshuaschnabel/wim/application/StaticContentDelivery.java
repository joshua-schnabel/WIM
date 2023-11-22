package de.joshuaschnabel.wim.application;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class StaticContentDelivery {

	@Bean
	public RouterFunction<ServerResponse> indexHtmlPlainRouter(
			@Value("classpath:/static-content/index.html") final Resource indexHtml) {
		return route(
				this.paths("/", "/*.html", "/*", "/*/*", "/*/*/*")
						.and(this.paths("/assets/**", "/api/**", "/favicon.ico").negate()),
				request -> ok().contentType(MediaType.TEXT_HTML).bodyValue(indexHtml));
	}

	private RequestPredicate paths(String... patterns) {
		var orPredicates = List.of(patterns).stream().map(RequestPredicates::path).reduce(RequestPredicate::or)
				.orElseThrow(IllegalArgumentException::new);
		return RequestPredicates.method(HttpMethod.GET).and(orPredicates);
	}

	@Bean
	public RouterFunction<ServerResponse> protectedResources() {
		return RouterFunctions.resources("/assets/protected/**", new ClassPathResource("static-content/protected/"));
	}

	@Bean
	public RouterFunction<ServerResponse> publicResources() {
		return RouterFunctions.resources("/assets/**", new ClassPathResource("static-content/public/"));
	}
}
