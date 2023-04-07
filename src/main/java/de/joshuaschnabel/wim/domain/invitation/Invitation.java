package de.joshuaschnabel.wim.domain.invitation;

import java.util.ArrayList;
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

	public static class InvitationName extends BasicType<String> {

		public InvitationName(String value) {
			super(value);
		}
	}

	private InvitationId id;

	private InvitationCode code;

	private InvitationName name;

	@Builder.Default
	private List<GuestStatus> guestStati = new ArrayList();

	private SpecialRequest specialRequest;

	private InvitationStatus status;

	public void addGuest(GuestId id) {
		this.guestStati.add(GuestStatus.builder().guest(id).build());
		EventBusProvider.getEventBus().publish(new GuestAddedEvent(id, this.getId()));
	}

	public void initializeNew() {
		this.id = InvitationId.getNewId();
		this.code = InvitationCode.generate();
		this.status = InvitationStatus.UNOPENED;
		this.setNew();
	}

	private void removeAllGuests() {
		var it = this.guestStati.iterator();
		while (it.hasNext()) {
			var element = it.next();
			EventBusProvider.getEventBus().publish(new GuestRemoveEvent(element.getGuest(), this.getId()));
			it.remove();
		}
	}

	public void removeGuest(GuestId id) {
		this.guestStati.removeIf(x -> x.getGuest().equals(id));
		EventBusProvider.getEventBus().publish(new GuestRemoveEvent(id, this.getId()));
	}

	@Override
	protected void setIdInternal(InvitationId id) {
		this.id = id;
	}

	public void uninitialize() {
		this.removeAllGuests();
	}

}
