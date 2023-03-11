package de.joshuaschnabel.wim.domain.ddd.generators;

import de.joshuaschnabel.wim.domain.ddd.objects.AggregateId;

public abstract class IdGenerator<Type, Id extends AggregateId<Type>> {

	public abstract Id generate();

	protected abstract Type generateID();

	public abstract boolean validate(Type id);

}
