package de.joshuaschnabel.wim.domain.guest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.joshuaschnabel.wim.domain.ddd.events.EventBus;
import de.joshuaschnabel.wim.domain.invitation.GuestAddedEvent;
import de.joshuaschnabel.wim.domain.invitation.GuestRemoveEvent;
import jakarta.annotation.PostConstruct;

@Component
public class GuestEventConsumer {

	@Autowired
	private EventBus eventBus;

	@Autowired
	private GuestRepository guestRepository;

	private void addInvitationToGuest(GuestAddedEvent event) {
		this.guestRepository.get(event.getGuestId()).map(guest -> guest.setInvitation(event.getInvitationId()))
				.map(guest -> this.guestRepository.store(guest)).flatMap(x -> x).subscribe();
	}

	@PostConstruct
	private void postConstruct() {
		this.eventBus.subscribe(GuestAddedEvent.class, this::addInvitationToGuest);
		this.eventBus.subscribe(GuestRemoveEvent.class, this::removeInvitationToGuest);
	}

	private void removeInvitationToGuest(GuestRemoveEvent event) {
		var guest = this.guestRepository.get(event.getGuestId());
		guest.map(Guest::removeInvitation).subscribe(x -> {
			if (x.getType() == GuestType.PrimaryGuest) {
				this.guestRepository.store(x).subscribe();
			} else {
				this.guestRepository.remove(x).subscribe();
			}
		});
	}

}
