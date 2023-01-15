package de.joshuaschnabel.weinfein.domain.guest;

import de.joshuaschnabel.weinfein.domain.ddd.AggregateId;

public class GuestId extends AggregateId<String> {

	public GuestId(String value) {
		super(value);
	}

}
