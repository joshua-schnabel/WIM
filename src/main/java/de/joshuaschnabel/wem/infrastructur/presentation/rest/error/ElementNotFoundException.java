package de.joshuaschnabel.wem.infrastructur.presentation.rest.error;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ElementNotFoundException extends HttpException {

	public static class ElementNotFoundExceptionBuilder {

		private Map<String, String> attributes = new HashMap<>();

		public ElementNotFoundException build() {
			return new ElementNotFoundException(this.attributes,
					this.functionalErrorCode);
		}

		public ElementNotFoundExceptionBuilder detail(String name,
				String value) {
			this.attributes.put(name, value);
			return this;
		}

		public ElementNotFoundExceptionBuilder elementId(String id) {
			this.attributes.put(ELEMENT_ID, id);
			return this;
		}
	}

	private static final long serialVersionUID = -3290666381969813739L;

	public static final String ELEMENT_ID = "ELEMENT_ID";

	private final Map<String, String> attributes;

	private final String functionalErrorCode;

}
