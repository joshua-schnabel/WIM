package de.joshuaschnabel.wem.domain.guest;

import de.joshuaschnabel.wem.domain.ddd.AggregateId;

public class GuestStatusId extends AggregateId<String> {

	public GuestStatusId(String value) {
		super(value);
	}

}
