package de.joshuaschnabel.wim.infrastructur.presentation.rest.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@EqualsAndHashCode
public class GuestStatusDTO {
	private String guestId;
	private Boolean accepted;
}