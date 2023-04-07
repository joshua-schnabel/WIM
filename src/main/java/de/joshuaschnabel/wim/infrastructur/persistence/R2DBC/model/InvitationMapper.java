package de.joshuaschnabel.wim.infrastructur.persistence.R2DBC.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import de.joshuaschnabel.wim.domain.invitation.Invitation;
import de.joshuaschnabel.wim.domain.invitation.InvitationCode;
import de.joshuaschnabel.wim.domain.invitation.InvitationId;
import de.joshuaschnabel.wim.infrastructur.persistence.R2DBC.model.pojo.InvitationPojo;

@Mapper(uses = GuestStatusMapper.class)
public interface InvitationMapper {

	default String invitationCodeTOString(InvitationCode invitationCode) {
		return invitationCode.getValue();
	}

	default String invitationIdTOString(InvitationId value) {
		return value.get();
	}

	@Mapping(source = "code", target = "code.value")
	@Mapping(source = "name", target = "name.value")
	@Mapping(source = "specialRequest", target = "specialRequest.request.value")
	@Mapping(source = "specialRequestAccepted", target = "specialRequest.accepted.value")
	@Mapping(source = "specialRequestAnswer", target = "specialRequest.answer.value")
	@Mapping(source = "guestStati", target = "guestStati")
	Invitation invitationPojoTOInvitation(InvitationPojo invitationPojo);

	@Mapping(source = "code.value", target = "code")
	@Mapping(source = "name.value", target = "name")
	@Mapping(source = "specialRequest.request.value", target = "specialRequest")
	@Mapping(source = "specialRequest.accepted.value", target = "specialRequestAccepted")
	@Mapping(source = "specialRequest.answer.value", target = "specialRequestAnswer")
	@Mapping(source = "create", target = "create")
	@Mapping(source = "guestStati", target = "guestStati")
	InvitationPojo invitationTOinvitationPojo(Invitation invitation);

	default InvitationId stringTOInvitationId(String value) {
		if (value == null) {
			return InvitationId.empty();
		}
		return InvitationId.of(value);
	};
}
