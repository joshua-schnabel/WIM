package de.joshuaschnabel.wim.domain.ddd.repositories;

import de.joshuaschnabel.wim.domain.ddd.objects.AggregateRoot;
import de.joshuaschnabel.wim.domain.ddd.objects.AggregateRootId;

public interface Repository<I extends AggregateRootId<?>, A extends AggregateRoot<I>>
    extends ReadRepository<I, A>, WriteRepository<I, A>, DeleteRepository<I, A> {


}
