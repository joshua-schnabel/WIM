package de.joshuaschnabel.wim.domain.invitation;

import de.joshuaschnabel.wim.domain.ddd.events.DomainEvent;
import de.joshuaschnabel.wim.domain.guest.GuestId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GuestAddedEvent extends DomainEvent {

	private GuestId guestId;
	private InvitationId invitationId;

	@Override
	public String getId() {
		return "";
	}

}
