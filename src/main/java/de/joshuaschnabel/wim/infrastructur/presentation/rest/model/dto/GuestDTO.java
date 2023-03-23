package de.joshuaschnabel.wim.infrastructur.presentation.rest.model.dto;

import org.springframework.hateoas.RepresentationModel;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@EqualsAndHashCode(callSuper = true)
public class GuestDTO extends RepresentationModel<GuestDTO> {

	private String id;
	private String firstname;
	private String lastname;
	private String guestType;

}