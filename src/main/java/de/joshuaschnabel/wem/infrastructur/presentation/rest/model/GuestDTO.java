package de.joshuaschnabel.wem.infrastructur.presentation.rest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class GuestDTO {

	private final String id;
	private final String firstname;
	private final String lastmame;
	private final String guestType;

}
