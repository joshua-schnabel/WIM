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
public class GuestStatusDTO extends RepresentationModel<GuestStatusDTO> {
	private String guestId;
	private Boolean accepted;
}