package de.joshuaschnabel.wim.infrastructur.presentation.rest.model.dto;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@EqualsAndHashCode(callSuper = true)
public class InvitationDTO extends RepresentationModel<InvitationDTO> {

	private String id;
	private String invitationCode;
	private String specialRequest;
	private Boolean specialRequestAccepted;
	private String specialRequestAnswer;
	private List<EntityModel<GuestStatusDTO>> guestStatus;

}
