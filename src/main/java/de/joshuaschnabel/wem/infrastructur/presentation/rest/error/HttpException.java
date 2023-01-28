package de.joshuaschnabel.wem.infrastructur.presentation.rest.error;

import java.util.Map;

public abstract class HttpException extends RuntimeException {

	private static final long serialVersionUID = -97825065576700908L;

	public abstract Map<String, String> getAttributes();
	public abstract String getFunctionalErrorCode();

}
