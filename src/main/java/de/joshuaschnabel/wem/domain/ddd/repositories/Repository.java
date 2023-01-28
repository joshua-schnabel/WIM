package de.joshuaschnabel.wem.domain.ddd.repositories;

import de.joshuaschnabel.wem.domain.ddd.objects.AggregateRoot;
import de.joshuaschnabel.wem.domain.ddd.objects.AggregateRootId;

public interface Repository<I extends AggregateRootId<?>, A extends AggregateRoot<I>>
    extends ReadRepository<I, A>, WriteRepository<I, A>, DeleteRepository<I, A> {


}
