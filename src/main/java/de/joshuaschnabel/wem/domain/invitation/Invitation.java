package de.joshuaschnabel.wem.domain.invitation;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import de.joshuaschnabel.wem.domain.ddd.events.EventBus;
import de.joshuaschnabel.wem.domain.ddd.objects.AggregateRoot;
import de.joshuaschnabel.wem.domain.guest.GuestId;
import de.joshuaschnabel.wem.domain.guest.GuestStatus;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
@EqualsAndHashCode(callSuper = false)
public class Invitation extends AggregateRoot<InvitationId> {

    private InvitationId id;

    private InvitationCode invitationCode;

    private List<GuestStatus> guestStati;

    // private Notification notification;

    private SpecialRequest specialRequest;

    @Autowired
    private EventBus eventBus;

    void addGuest(GuestId id) {
        this.guestStati.add(GuestStatus.builder().guest(id).build());
        this.eventBus.publish(new GuestAddedEvent());
    }

    @Override
    protected void setIdInternal(InvitationId id) {
        this.id = id;
    }

}
