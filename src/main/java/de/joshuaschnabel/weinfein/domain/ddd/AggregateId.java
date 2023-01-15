package de.joshuaschnabel.weinfein.domain.ddd;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class AggregateId<ValueType> {

	private final ValueType value;

	public ValueType get() {
		return this.value;
	}
}
