package de.joshuaschnabel.wim.infrastructur.presentation.rest.model;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import de.joshuaschnabel.wim.domain.guest.GuestStatus;
import de.joshuaschnabel.wim.domain.invitation.Invitation;
import de.joshuaschnabel.wim.domain.invitation.InvitationId;
import de.joshuaschnabel.wim.infrastructur.presentation.rest.model.dto.InvitationDTO;

@Mapper(uses = GuestStatusMapper.class)
public interface InvitationMapper {

	@Mapping(target = "guestStati", ignore = true)
	@Mapping(source = "code", target = "code.value")
	@Mapping(source = "name", target = "name.value")
	@Mapping(source = "specialRequest", target = "specialRequest.request.value")
	@Mapping(source = "specialRequestAccepted", target = "specialRequest.accepted.value")
	@Mapping(source = "specialRequestAnswer", target = "specialRequest.answer.value")
	Invitation invitationDTOTOInvitation(InvitationDTO invitationDTO);

	default String InvitationIdTOString(InvitationId value) {
		return value.get();
	}

	@Mapping(source = "guestStati", target = "assignedGuestsCount", qualifiedByName = "toAssignedGuestsCount")
	@Mapping(source = "guestStati", target = "confirmedGuestsCount", qualifiedByName = "toConfirmedGuestsCount")
	@Mapping(source = "code.value", target = "code")
	@Mapping(source = "name.value", target = "name")
	@Mapping(source = "specialRequest.request.value", target = "specialRequest")
	@Mapping(source = "specialRequest.accepted.value", target = "specialRequestAccepted")
	@Mapping(source = "specialRequest.answer.value", target = "specialRequestAnswer")
	InvitationDTO invitationTOinvitationDTO(Invitation invitation);

	default InvitationId stringTOInvitationId(String value) {
		if (value == null) {
			return InvitationId.empty();
		}
		return InvitationId.of(value);
	}

	@Named("toAssignedGuestsCount")
	default int toAssignedGuestsCount(List<GuestStatus> guestStati) {
		return guestStati.size();
	}

	@Named("toConfirmedGuestsCount")
	default int toConfirmedGuestsCount(List<GuestStatus> guestStati) {
		return (int) guestStati.stream().filter(x -> x.getAccepted().getValue()).count();
	}
}
