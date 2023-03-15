package de.joshuaschnabel.wim.infrastructur.presentation.rest.model;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.hateoas.EntityModel;

import de.joshuaschnabel.wim.domain.guest.GuestStatus;
import de.joshuaschnabel.wim.domain.invitation.Invitation;
import de.joshuaschnabel.wim.domain.invitation.InvitationId;
import de.joshuaschnabel.wim.infrastructur.presentation.rest.model.dto.GuestStatusDTO;
import de.joshuaschnabel.wim.infrastructur.presentation.rest.model.dto.InvitationDTO;

@Mapper(uses = GuestStatusMapper.class)
public interface InvitationMapper {

	@Mapping(target = "guestStati", ignore = true)
	@Mapping(source = "invitationCode", target = "invitationCode.value")
	@Mapping(source = "specialRequest", target = "specialRequest.request.value")
	@Mapping(source = "specialRequestAccepted", target = "specialRequest.accepted.value")
	@Mapping(source = "specialRequestAnswer", target = "specialRequest.answer.value")
	Invitation invitationDTOTOInvitation(InvitationDTO invitationDTO);

	default String InvitationIdTOString(InvitationId value) {
		return value.get();
	}

	@Mapping(source = "guestStati", target = "guestStatus", qualifiedByName = "wrap")
	@Mapping(source = "invitationCode.value", target = "invitationCode")
	@Mapping(source = "specialRequest.request.value", target = "specialRequest")
	@Mapping(source = "specialRequest.accepted.value", target = "specialRequestAccepted")
	@Mapping(source = "specialRequest.answer.value", target = "specialRequestAnswer")
	InvitationDTO invitationTOinvitationDTO(Invitation invitation);

	default InvitationId stringTOInvitationId(String value) {
		return InvitationId.of(value);
	}

	@Named("wrap")
	default List<EntityModel<GuestStatusDTO>> wrap(List<GuestStatus> value) {
		var mapper = Mappers.getMapper(GuestStatusMapper.class);
		return value.stream().map(x -> mapper.guestStatusTOGuestStatusDTO(x)).map(EntityModel::of)
				.collect(Collectors.toList());
	}
}
