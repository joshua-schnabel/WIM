package de.joshuaschnabel.wim.infrastructur.presentation.rest.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@EqualsAndHashCode
public class GuestDTO {

	private String id;
	private String firstname;
	private String lastname;
	private String guestType;
	private Boolean assigned;

}
