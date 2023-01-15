package de.joshuaschnabel.weinfein.domain.guest;

import de.joshuaschnabel.weinfein.domain.ddd.AggregateId;

public class GuestStatusId extends AggregateId<String> {

	public GuestStatusId(String value) {
		super(value);
	}

}
