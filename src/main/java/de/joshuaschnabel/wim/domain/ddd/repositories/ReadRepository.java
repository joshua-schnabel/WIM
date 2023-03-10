package de.joshuaschnabel.wim.domain.ddd.repositories;

import de.joshuaschnabel.wim.domain.ddd.objects.AggregateRoot;
import de.joshuaschnabel.wim.domain.ddd.objects.AggregateRootId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReadRepository<I extends AggregateRootId<?>, A extends AggregateRoot<I>> {

    public Mono<A> get(I identity);

    public Flux<A> getAll();

}
