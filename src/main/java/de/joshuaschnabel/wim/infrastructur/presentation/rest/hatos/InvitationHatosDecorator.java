package de.joshuaschnabel.wim.infrastructur.presentation.rest.hatos;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.reactive.WebFluxLinkBuilder;

import de.joshuaschnabel.wim.infrastructur.presentation.rest.controllers.InvitationController;
import de.joshuaschnabel.wim.infrastructur.presentation.rest.model.dto.InvitationDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class InvitationHatosDecorator {

	private final InvitationController controller = WebFluxLinkBuilder.methodOn(InvitationController.class);

	public Mono<EntityModel<InvitationDTO>> addLinks(Mono<InvitationDTO> element) {
		return Flux.concat(element).flatMap(this::addLinksToDTO).single();
	}

	private Mono<EntityModel<InvitationDTO>> addLinksToDTO(InvitationDTO dto) {
		final var selfLink = linkTo(this.controller.findOne(dto.getId())).withSelfRel() //
				.andAffordance(this.controller.updateInvitation(null, dto.getId()))//
				.andAffordance(this.controller.deleteOne(dto.getId()))//
				.toMono();
		final var employeesLink = linkTo(this.controller.all()).withRel("invitations") //
				.toMono();
		final var guestsLink = linkTo(this.controller.allGuests(dto.getId())).withRel("guests") //
				.toMono();
		final List<Mono<Link>> links = List.of(selfLink, employeesLink, guestsLink);
		return Flux.concat(links).collectList().map(link -> EntityModel.of(dto, link));
	}

	public Mono<CollectionModel<EntityModel<InvitationDTO>>> addListLinks(Flux<InvitationDTO> elements) {
		final Flux<EntityModel<InvitationDTO>> flatMap = elements.flatMap(this::addLinksToDTO);
		return flatMap //
				.collectList() //
				.flatMap(resources -> linkTo(this.controller.all()).withSelfRel() //
						.andAffordance(this.controller.newInvitation(null)).toMono() //
						.map(selfLink -> CollectionModel.of(resources, selfLink)));
	}

}
