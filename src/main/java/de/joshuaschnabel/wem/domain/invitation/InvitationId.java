package de.joshuaschnabel.wem.domain.invitation;

import de.joshuaschnabel.wem.domain.ddd.objects.AggregateRootId;

public class InvitationId extends AggregateRootId<String> {

	public static InvitationId of(String id) {
		return new InvitationId(id);
	}

	protected InvitationId(String value) {
		super(value);
	}

}
