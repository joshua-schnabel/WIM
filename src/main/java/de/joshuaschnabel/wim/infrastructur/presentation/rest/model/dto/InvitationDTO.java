package de.joshuaschnabel.wim.infrastructur.presentation.rest.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@EqualsAndHashCode
public class InvitationDTO {

	private String id;
	private String code;
	private String name;
	private String specialRequest;
	private Boolean specialRequestAccepted;
	private String specialRequestAnswer;
	private String status;
	private final int assignedGuestsCount;
	private final int confirmedGuestsCount;
}
