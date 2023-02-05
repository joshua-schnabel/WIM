package de.joshuaschnabel.wem.domain.ddd.generators;

import de.joshuaschnabel.wem.domain.ddd.objects.AggregateId;

public abstract class IdGenerator<Type, Id extends AggregateId<Type>> {

	public abstract Id generate();

	protected abstract Type generateID();

	public abstract boolean validate(Type id);

}
