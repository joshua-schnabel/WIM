package de.joshuaschnabel.wem.domain.ddd.repositories;

import de.joshuaschnabel.wem.domain.ddd.objects.AggregateRoot;
import de.joshuaschnabel.wem.domain.ddd.objects.AggregateRootId;
import reactor.core.publisher.Mono;

public interface ReadRepository<I extends AggregateRootId<?>, A extends AggregateRoot<I>> {

  public Mono<A> get(I identity);

}
