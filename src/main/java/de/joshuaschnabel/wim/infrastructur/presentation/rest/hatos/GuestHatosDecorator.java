package de.joshuaschnabel.wim.infrastructur.presentation.rest.hatos;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
import java.util.List;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.reactive.WebFluxLinkBuilder;
import de.joshuaschnabel.wim.infrastructur.presentation.rest.controllers.GuestController;
import de.joshuaschnabel.wim.infrastructur.presentation.rest.model.dto.GuestDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class GuestHatosDecorator {

	private final GuestController controller = WebFluxLinkBuilder
			.methodOn(GuestController.class);

	public Mono<EntityModel<GuestDTO>> addLinks(Mono<GuestDTO> element) {
		return Flux.concat(element).flatMap(this::addLinksToDTO).single();
	}

	private Mono<EntityModel<GuestDTO>> addLinksToDTO(GuestDTO dto) {
		final var selfLink = linkTo(this.controller.findOne(dto.getId()))
				.withSelfRel() //
				.andAffordance(this.controller.updateGuest(null, dto.getId()))//
				.andAffordance(this.controller.deleteOne( dto.getId()))//
				.toMono();
		final var employeesLink = linkTo(this.controller.all())
				.withRel("guests") //
				.toMono();
		final List<Mono<Link>> links = List.of(selfLink, employeesLink);
		return Flux.concat(links).collectList()
				.map(link -> EntityModel.of(dto, link));
	}

	public Mono<CollectionModel<EntityModel<GuestDTO>>> addListLinks(
			Flux<GuestDTO> elements) {
		final Flux<EntityModel<GuestDTO>> flatMap = elements
				.flatMap(this::addLinksToDTO);
		return flatMap //
				.collectList() //
				.flatMap(resources -> linkTo(this.controller.all())
						.withSelfRel() //
						.andAffordance(this.controller.newGuest(null)).toMono() //
						.map(selfLink -> CollectionModel.of(resources,
								selfLink)));
	}

}
