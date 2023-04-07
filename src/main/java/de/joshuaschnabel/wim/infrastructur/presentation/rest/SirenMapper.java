package de.joshuaschnabel.wim.infrastructur.presentation.rest;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;

import de.joshuaschnabel.wim.domain.ddd.objects.AggregateRoot;
import de.joshuaschnabel.wim.domain.ddd.objects.AggregateRootId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SirenMapper<DomainObject extends AggregateRoot<DomainID>, DomainID extends AggregateRootId<?>, DtoOpject> {

	public DomainObject map(DtoOpject dto);

	public Mono<CollectionModel<RepresentationModel<?>>> map(Flux<DomainObject> elements);

	public Mono<RepresentationModel<?>> map(Mono<DomainObject> element);

	public DomainID map(String dto);
}
