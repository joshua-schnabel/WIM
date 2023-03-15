package de.joshuaschnabel.wim.infrastructur.presentation.rest.controllers;

import java.util.function.Function;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import de.joshuaschnabel.wim.domain.invitation.Invitation;
import de.joshuaschnabel.wim.domain.invitation.InvitationRepository;
import de.joshuaschnabel.wim.infrastructur.presentation.rest.error.ElementNotFoundException;
import de.joshuaschnabel.wim.infrastructur.presentation.rest.hatos.InvitationHatosDecorator;
import de.joshuaschnabel.wim.infrastructur.presentation.rest.model.InvitationMapper;
import de.joshuaschnabel.wim.infrastructur.presentation.rest.model.dto.InvitationDTO;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "api/invitations")
public class InvitationController {

	private InvitationMapper mapper = Mappers.getMapper(InvitationMapper.class);

	@Autowired
	private InvitationRepository invitationRepository;

	private final InvitationHatosDecorator hatosDecorator = new InvitationHatosDecorator();

	@GetMapping(path = "/")
	public Mono<CollectionModel<EntityModel<InvitationDTO>>> all() {
		final var elements = this.invitationRepository.getAll().map(this.mapToInvitationDto());
		return this.hatosDecorator.addListLinks(elements);
	}

	@GetMapping(path = "/{id}/guests")
	public Mono<EntityModel<InvitationDTO>> allGuests(@PathVariable String id) {
		throw new IllegalAccessError();
	}

	@DeleteMapping(path = "/{id}")
	public Mono<ResponseEntity<?>> deleteOne(@PathVariable String id) {
		final var invitationId = this.mapper.stringTOInvitationId(id);
		return this.invitationRepository.remove(invitationId).map(result -> {
			if (result) {
				return ResponseEntity.ok().build();
			}
			return ResponseEntity.notFound().build();
		});
	}

	@GetMapping(path = "/{id}")
	public Mono<EntityModel<InvitationDTO>> findOne(@PathVariable String id) {
		final var invitationId = this.mapper.stringTOInvitationId(id);
		final var element = this.invitationRepository.get(invitationId)
				// If Invitation not found
				.switchIfEmpty(Mono.error(ElementNotFoundException.builder().elementId(id)
						.functionalErrorCode("invitation_not_found").build()))
				.map(this.mapToInvitationDto());
		return this.hatosDecorator.addLinks(element);
	}

	private Invitation mapToInvitation(InvitationDTO resource) {
		return this.mapper.invitationDTOTOInvitation(resource);
	}

	private Function<Invitation, InvitationDTO> mapToInvitationDto() {
		return x -> this.mapper.invitationTOinvitationDTO(x);
	}

	@PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Mono<EntityModel<InvitationDTO>> newInvitation(@RequestBody Mono<EntityModel<InvitationDTO>> invitationDTO) {
		return invitationDTO.flatMap(resource -> {
			final var dto = this.mapToInvitation(resource.getContent());
			final var object = this.invitationRepository.store(dto);
			return this.hatosDecorator.addLinks(object.map(this.mapToInvitationDto()));
		});
	}

	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Mono<EntityModel<InvitationDTO>> updateInvitation(
			@RequestBody Mono<EntityModel<InvitationDTO>> invitationDTO, @PathVariable String id) {
		final var invitationId = this.mapper.stringTOInvitationId(id);
		final var invitation = invitationDTO.flatMap(resource -> {
			final var dto = this.mapToInvitation(resource.getContent());
			if (!invitationId.equals(dto.getId())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			}
			final var object = this.invitationRepository.store(dto);
			return object.map(this.mapToInvitationDto());
		});
		return this.hatosDecorator.addLinks(invitation);
	}
}
