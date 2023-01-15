package de.joshuaschnabel.wem.domain.guest;

import de.joshuaschnabel.wem.domain.ddd.AggregateId;

public class GuestId extends AggregateId<String> {

	public GuestId(String value) {
		super(value);
	}

}
