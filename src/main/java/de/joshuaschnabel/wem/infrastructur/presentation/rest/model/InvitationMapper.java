package de.joshuaschnabel.wem.infrastructur.presentation.rest.model;

import java.util.function.Function;
import de.joshuaschnabel.wem.domain.invitation.Invitation;
import de.joshuaschnabel.wem.domain.invitation.InvitationId;

public class InvitationMapper implements DtoMapper<Invitation, InvitationDTO, InvitationId, String> {

    @Override
    public Function<InvitationDTO, Invitation> mapToDomainFn() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Function<String, InvitationId> mapToDomainIdFn() {
        return InvitationId::of;
    }

    @Override
    public Function<Invitation, InvitationDTO> mapToDtoFn() {

        return null;
    }

    @Override
    public Function<InvitationId, String> mapToDtoIdFn() {
        return InvitationId::get;
    }

}
