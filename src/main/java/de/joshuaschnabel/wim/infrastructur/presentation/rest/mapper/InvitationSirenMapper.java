package de.joshuaschnabel.wim.infrastructur.presentation.rest.mapper;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;

import java.util.List;

import org.mapstruct.factory.Mappers;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.reactive.WebFluxLinkBuilder;

import de.ingogriebsch.spring.hateoas.siren.SirenModelBuilder;
import de.joshuaschnabel.wim.domain.guest.GuestStatus;
import de.joshuaschnabel.wim.domain.invitation.Invitation;
import de.joshuaschnabel.wim.domain.invitation.InvitationId;
import de.joshuaschnabel.wim.infrastructur.presentation.rest.SirenMapper;
import de.joshuaschnabel.wim.infrastructur.presentation.rest.controllers.GuestController;
import de.joshuaschnabel.wim.infrastructur.presentation.rest.controllers.InvitationController;
import de.joshuaschnabel.wim.infrastructur.presentation.rest.model.GuestStatusMapper;
import de.joshuaschnabel.wim.infrastructur.presentation.rest.model.InvitationMapper;
import de.joshuaschnabel.wim.infrastructur.presentation.rest.model.dto.InvitationDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

public class InvitationSirenMapper implements SirenMapper<Invitation, InvitationId, InvitationDTO> {

	private final InvitationMapper invitationMapper = Mappers.getMapper(InvitationMapper.class);
	private final GuestStatusMapper guestStatusMapper = Mappers.getMapper(GuestStatusMapper.class);

	private final InvitationController controller = WebFluxLinkBuilder.methodOn(InvitationController.class);
	private final GuestController controllerGuest = WebFluxLinkBuilder.methodOn(GuestController.class);

	private Mono<RepresentationModel<?>> addLinksToDTO(Invitation domain) {
		final var id = this.invitationMapper.InvitationIdTOString(domain.getId());
		final var selfLink = linkTo(this.controller.self(id)).withSelfRel() //
				.andAffordance(this.controller.update(null, id))//
				.andAffordance(this.controller.delete(id))//
				.toMono();
		final var listLink = linkTo(this.controller.get()).withRel("invitations") //
				.toMono();
		final var guestsLink = linkTo(this.controller.getGuests(id)).withRel("guests") //
				.toMono();
		final var guestsAddLink = linkTo(this.controller.addGuest(id, null)).withRel("guests") //
				.toMono();
		final var links = Flux.concat(List.of(selfLink, listLink, guestsLink, guestsAddLink)).collectList();
		final var guestRP = this.generateGuestRP(domain);
		final var dto = Mono.just(this.invitationMapper.invitationTOinvitationDTO(domain));
		return Mono.zip(dto, links, guestRP).map(tp -> SirenModelBuilder.sirenModel().linksAndActions(tp.getT2())
				.properties(tp.getT1()).entities(tp.getT3()).build());
	}

	private Mono<Object> generateGuestRP(Invitation domain) {
		final var id = this.invitationMapper.InvitationIdTOString(domain.getId());
		final var linksToGuests = linkTo(this.controller.getGuests(id)).withRel("guests").toMono();
		Flux<Link> links = Flux.fromIterable(domain.getGuestStati())
				.map(z -> linkTo(this.controllerGuest.findGuest(z.getGuest().get())).withSelfRel().toMono())
				.flatMap(mono -> mono);
		Flux<RepresentationModel<?>> models = Flux.zip(Flux.fromIterable(domain.getGuestStati()), links)
				.map(this::xxxx);
		var modelsM = models.collectList();
		return Mono.zip(modelsM, linksToGuests).map(tp -> CollectionModel.of(tp.getT1(), tp.getT2()));
	}

	@Override
	public Mono<CollectionModel<RepresentationModel<?>>> map(Flux<Invitation> elements) {
		final Flux<RepresentationModel<?>> flatMap = elements.flatMap(this::addLinksToDTO);
		return flatMap //
				.collectList() //
				.flatMap(resources -> linkTo(this.controller.get()).withSelfRel() //
						.andAffordance(this.controller.create(null)).toMono() //
						.map(selfLink -> CollectionModel.of(resources, selfLink)));
	}

	@Override
	public Invitation map(InvitationDTO dto) {
		return this.invitationMapper.invitationDTOTOInvitation(dto);
	}

	@Override
	public Mono<RepresentationModel<?>> map(Mono<Invitation> element) {
		return Flux.concat(element).flatMap(this::addLinksToDTO).single();
	}

	@Override
	public InvitationId map(String dto) {
		return this.invitationMapper.stringTOInvitationId(dto);
	}

	public Mono<CollectionModel<RepresentationModel<?>>> mapGuestStati(Invitation domain) {
		final var id = this.invitationMapper.InvitationIdTOString(domain.getId());
		final var linksToGuests = linkTo(this.controller.getGuests(id)).withRel("guests").toMono();
		Flux<Link> links = Flux.fromIterable(domain.getGuestStati())
				.map(z -> linkTo(this.controllerGuest.findGuest(z.getGuest().get())).withSelfRel().toMono())
				.flatMap(mono -> mono);
		Flux<RepresentationModel<?>> models = Flux.zip(Flux.fromIterable(domain.getGuestStati()), links)
				.map(this::xxxx);
		var modelsM = models.collectList();
		return Mono.zip(modelsM, linksToGuests).map(tp -> CollectionModel.of(tp.getT1(), tp.getT2()));
	}

	private RepresentationModel<?> xxxx(Tuple2<GuestStatus, Link> tp) {
		return SirenModelBuilder.sirenModel().properties(this.guestStatusMapper.guestStatusTOGuestStatusDTO(tp.getT1()))
				.linksAndActions(tp.getT2()).build();
	}

}
