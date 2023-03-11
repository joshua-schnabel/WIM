package de.joshuaschnabel.wim.domain.invitation;

import java.util.List;
import de.joshuaschnabel.wim.domain.ddd.events.EventBusProvider;
import de.joshuaschnabel.wim.domain.ddd.objects.AggregateRoot;
import de.joshuaschnabel.wim.domain.ddd.type.BasicType;
import de.joshuaschnabel.wim.domain.guest.GuestId;
import de.joshuaschnabel.wim.domain.guest.GuestStatus;
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
