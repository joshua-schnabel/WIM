package de.joshuaschnabel.wem.domain.invitation;

import java.util.List;
import de.joshuaschnabel.wem.domain.ddd.events.EventBusProvider;
import de.joshuaschnabel.wem.domain.ddd.objects.AggregateRoot;
import de.joshuaschnabel.wem.domain.ddd.type.BasicType;
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

    public static class InvitationCode extends BasicType<String> {

        public InvitationCode(String value) {
            super(value);
        }
    }

    private InvitationId id;

    private InvitationCode invitationCode;

    // private Notification notification;

    private List<GuestStatus> guestStati;

    private SpecialRequest specialRequest;

    void addGuest(GuestId id) {
        this.guestStati.add(GuestStatus.builder().guest(id).build());
        EventBusProvider.getEventBus().publish(new GuestAddedEvent());
    }

    @Override
    protected void setIdInternal(InvitationId id) {
        this.id = id;
    }


}
