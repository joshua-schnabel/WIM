package de.joshuaschnabel.wem.domain.ddd.repositories;

import de.joshuaschnabel.wem.domain.ddd.objects.AggregateRoot;
import de.joshuaschnabel.wem.domain.ddd.objects.AggregateRootId;
import reactor.core.publisher.Mono;

public interface DeleteRepository<I extends AggregateRootId<?>, A extends AggregateRoot<I>> {

  public Mono<Void> remove(A aggregate);

  public Mono<Void> remove(I identity);
}
