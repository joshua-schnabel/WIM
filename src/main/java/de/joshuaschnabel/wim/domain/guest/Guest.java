package de.joshuaschnabel.wim.domain.guest;

import java.util.Optional;

import de.joshuaschnabel.wim.domain.DomainErrors;
import de.joshuaschnabel.wim.domain.ddd.objects.AggregateRoot;
import de.joshuaschnabel.wim.domain.ddd.objects.IllegalObjectModificationException;
import de.joshuaschnabel.wim.domain.invitation.InvitationId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Guest extends AggregateRoot<GuestId> {

	@Getter
	private GuestId id;

	private GuestName name;

	@Getter
	private final GuestType type;

	@Getter
	@Builder.Default
	private Optional<InvitationId> invitation = Optional.empty();

	public Guest(GuestId id, GuestName name, GuestType type, InvitationId invitation) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.invitation = Optional.of(invitation);
	}

	public Optional<GuestName> getName() {
		return Optional.ofNullable(this.name);
	}

	/**
	 * Required because of mapstruct
	 *
	 * @return
	 */
	public GuestName getNameNullable() {
		return this.name;
	}

	public void initializeNew() {
		this.id = GuestId.getNewId();
		this.setNew();
	}

	protected Guest removeInvitation() {
		this.invitation = Optional.empty();
		return this;
	}

	@Override
	protected void setIdInternal(GuestId id) {
		this.id = id;
	};

	protected Guest setInvitation(InvitationId id) {
		this.invitation = Optional.of(id);
		return this;
	};

	public void setName(GuestName name) {
		if (this.type == GuestType.PrimaryGuest) {
			throw new IllegalObjectModificationException(DomainErrors.GUEST_name_cannot_be_changed_afterwards);
		}
		this.name = name;
	}
}
