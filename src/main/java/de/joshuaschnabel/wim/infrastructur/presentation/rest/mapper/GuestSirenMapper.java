package de.joshuaschnabel.wim.infrastructur.presentation.rest.mapper;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;

import java.util.List;

import org.mapstruct.factory.Mappers;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.reactive.WebFluxLinkBuilder;

import de.ingogriebsch.spring.hateoas.siren.SirenModelBuilder;
import de.joshuaschnabel.wim.domain.guest.Guest;
import de.joshuaschnabel.wim.domain.guest.GuestId;
import de.joshuaschnabel.wim.infrastructur.presentation.rest.SirenMapper;
import de.joshuaschnabel.wim.infrastructur.presentation.rest.controllers.GuestController;
import de.joshuaschnabel.wim.infrastructur.presentation.rest.model.GuestMapper;
import de.joshuaschnabel.wim.infrastructur.presentation.rest.model.dto.GuestDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class GuestSirenMapper implements SirenMapper<Guest, GuestId, GuestDTO> {

	private final GuestMapper guestMapper = Mappers.getMapper(GuestMapper.class);
	private final GuestController controllerGuest = WebFluxLinkBuilder.methodOn(GuestController.class);

	private Mono<RepresentationModel<?>> addLinksToDTO(Guest domain) {
		final var id = this.guestMapper.guestIdToString(domain.getId());
		final var selfLink = linkTo(this.controllerGuest.findGuest(id)).withSelfRel().toMono();
		final var employeesLink = linkTo(this.controllerGuest.all()).withRel("guests") //
				.toMono();
		final var deleteLink = linkTo(this.controllerGuest.deleteGuest(id)).withRel("delete") //
				.toMono();
		final var links = Flux.concat(List.of(selfLink, employeesLink, deleteLink)).collectList();
		final var dto = Mono.just(this.guestMapper.guestTOguestDTO(domain));
		return Mono.zip(dto, links)
				.map(tp -> SirenModelBuilder.sirenModel().linksAndActions(tp.getT2()).properties(tp.getT1()).build());
	}

	@Override
	public Mono<CollectionModel<RepresentationModel<?>>> map(Flux<Guest> elements) {
		final Flux<RepresentationModel<?>> flatMap = elements.flatMap(this::addLinksToDTO);
		return flatMap //
				.collectList() //
				.flatMap(resources -> linkTo(this.controllerGuest.all()).withSelfRel() //
						.andAffordance(this.controllerGuest.createGuest(null)).toMono() //
						.map(selfLink -> CollectionModel.of(resources, selfLink)));
	}

	@Override
	public Guest map(GuestDTO dto) {
		return this.guestMapper.guestDTOTOguest(dto);
	}

	@Override
	public Mono<RepresentationModel<?>> map(Mono<Guest> element) {
		return Flux.concat(element).flatMap(this::addLinksToDTO).single();
	}

	@Override
	public GuestId map(String dto) {
		return GuestId.of(dto);
	}

}
