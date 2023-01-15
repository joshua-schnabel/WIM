package de.joshuaschnabel.wem.infrastructur.presentation.rest.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class GuestDTO {

	String firstname;
	String lastmame;

}
