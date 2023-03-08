package de.joshuaschnabel.wem.domain.invitation;

import de.joshuaschnabel.wem.domain.ddd.events.DomainEvent;
import de.joshuaschnabel.wem.domain.guest.GuestId;
import lombok.Getter;

@Getter
public class GuestAddedEvent extends DomainEvent {

    private GuestId guestId;
    private InvitationId invitationId;

    @Override
    public String getId() {
        return "";
    }

}
