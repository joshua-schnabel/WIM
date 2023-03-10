package de.joshuaschnabel.wem.infrastructur.presentation.rest.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import de.joshuaschnabel.wem.domain.invitation.Invitation;
import de.joshuaschnabel.wem.domain.invitation.InvitationId;
import de.joshuaschnabel.wem.infrastructur.presentation.rest.model.dto.InvitationDTO;

@Mapper(uses = {GuestStatusMapper.class})
public interface InvitationMapper {

    @Mapping(source = "invitationCode", target = "invitationCode.value")
    @Mapping(source = "specialRequest", target = "specialRequest.request.value")
    @Mapping(source = "specialRequestAccepted", target = "specialRequest.accepted.value")
    @Mapping(source = "specialRequestAnswer", target = "specialRequest.answer.value")
    Invitation invitationDTOTOInvitation(InvitationDTO invitationDTO);

    @Mapping(source = "invitationCode.value", target = "invitationCode")
    @Mapping(source = "specialRequest.request.value", target = "specialRequest")
    @Mapping(source = "specialRequest.accepted.value", target = "specialRequestAccepted")
    @Mapping(source = "specialRequest.answer.value", target = "specialRequestAnswer")
    InvitationDTO invitationTOinvitationDTO(Invitation invitation);

    default String map(InvitationId value) {
        return value.get();
    }

    default InvitationId map(String value) {
        return InvitationId.of(value);
    }

}
