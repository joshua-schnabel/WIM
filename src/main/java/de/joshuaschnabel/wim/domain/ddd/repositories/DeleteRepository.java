package de.joshuaschnabel.wim.domain.ddd.repositories;

import de.joshuaschnabel.wim.domain.ddd.objects.AggregateRoot;
import de.joshuaschnabel.wim.domain.ddd.objects.AggregateRootId;
import reactor.core.publisher.Mono;

public interface DeleteRepository<I extends AggregateRootId<?>, A extends AggregateRoot<I>> {

	public Mono<Boolean> remove(A aggregate);

	public Mono<Boolean> remove(I identity);
}
