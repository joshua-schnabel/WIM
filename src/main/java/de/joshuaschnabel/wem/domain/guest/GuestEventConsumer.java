package de.joshuaschnabel.wem.domain.guest;

import org.springframework.beans.factory.annotation.Autowired;
import de.joshuaschnabel.wem.domain.ddd.events.EventBus;
import de.joshuaschnabel.wem.domain.ddd.events.EventConsumer;
import de.joshuaschnabel.wem.domain.invitation.GuestAddedEvent;

@EventConsumer
public class GuestEventConsumer {

    @Autowired
    private EventBus eventBus;

    @Autowired
    private GuestRepository guestRepository;

    public GuestEventConsumer() {
        this.eventBus.subscribe(GuestAddedEvent.class, this::addInvitationToGuest);
    }

    private void addInvitationToGuest(GuestAddedEvent event) {
        this.guestRepository.get(event.getGuestId()).map(guest -> guest.setInvitation(event.getInvitationId()))
                .subscribe(guest -> this.guestRepository.store(guest));
    }

}
