package de.joshuaschnabel.wim.infrastructur.presentation.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
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

import de.joshuaschnabel.wim.domain.guest.GuestRepository;
import de.joshuaschnabel.wim.domain.invitation.InvitationRepository;
import de.joshuaschnabel.wim.infrastructur.presentation.rest.error.ElementNotFoundException;
import de.joshuaschnabel.wim.infrastructur.presentation.rest.mapper.GuestSirenMapper;
import de.joshuaschnabel.wim.infrastructur.presentation.rest.mapper.InvitationSirenMapper;
import de.joshuaschnabel.wim.infrastructur.presentation.rest.model.dto.InvitationDTO;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "api/invitations")
public class InvitationController {

	@Autowired
	private InvitationRepository invitationRepository;

	@Autowired
	private GuestRepository guestRepository;

	private final InvitationSirenMapper sirenMapper = new InvitationSirenMapper();
	private final GuestSirenMapper sirenGuestMapper = new GuestSirenMapper();

	@PostMapping(path = "/{id}/guests", consumes = MediaType.TEXT_PLAIN_VALUE)
	public Mono<ResponseEntity<?>> addGuest(@PathVariable String id, @RequestBody String guestIdIn) {
		final var invitationId = this.sirenMapper.map(id);
		final var guestId = this.sirenGuestMapper.map(guestIdIn);
		final var element = this.invitationRepository.get(invitationId)
				// If Invitation not found
				.switchIfEmpty(Mono.error(ElementNotFoundException.builder().elementId(invitationId.get())
						.functionalErrorCode("invitation_not_found").build()));
		final var guest = this.guestRepository.get(guestId)
				// If Guest not found
				.switchIfEmpty(Mono.error(ElementNotFoundException.builder().elementId(guestId.get())
						.functionalErrorCode("guest_not_found").build()));
		return Mono.zip(element, guest).map(tp -> {
			tp.getT1().addGuest(tp.getT2().getId());
			return this.invitationRepository.store(tp.getT1());
		}).flatMap(mono -> mono).map(x -> ResponseEntity.ok().build());
	}

	@PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Mono<RepresentationModel<?>> create(@RequestBody Mono<InvitationDTO> invitationDTO) {
		return invitationDTO.flatMap(resource -> {
			if (resource.getId() != null || resource.getCode() != null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			}
			final var dto = this.sirenMapper.map(resource);
			dto.initializeNew();
			final var object = this.invitationRepository.store(dto);
			return this.sirenMapper.map(object);
		});
	}

	@DeleteMapping(path = "/{id}")
	public Mono<ResponseEntity<?>> delete(@PathVariable String id) {
		final var invitationId = this.sirenMapper.map(id);
		return this.invitationRepository.get(invitationId).map(x -> {
			x.uninitialize();
			return this.invitationRepository.remove(x);
		}).flatMap(x -> x).map(result -> {
			if (result) {
				return ResponseEntity.ok().build();
			}
			return ResponseEntity.notFound().build();
		});
	}

	@DeleteMapping(path = "/{id}/guests", consumes = MediaType.TEXT_PLAIN_VALUE)
	public Mono<ResponseEntity<?>> deleteGuest(@PathVariable String id, @RequestBody String guestIdIn) {
		final var invitationId = this.sirenMapper.map(id);
		final var guestId = this.sirenGuestMapper.map(guestIdIn);
		final var element = this.invitationRepository.get(invitationId)
				// If Invitation not found
				.switchIfEmpty(Mono.error(ElementNotFoundException.builder().elementId(invitationId.get())
						.functionalErrorCode("invitation_not_found").build()));
		final var guest = this.guestRepository.get(guestId)
				// If Guest not found
				.switchIfEmpty(Mono.error(ElementNotFoundException.builder().elementId(guestId.get())
						.functionalErrorCode("guest_not_found").build()));
		return Mono.zip(element, guest).map(tp -> {
			tp.getT1().removeGuest(tp.getT2().getId());
			return tp.getT1();
		}).map(x -> ResponseEntity.ok().build());
	}

	@GetMapping(path = "/")
	public Mono<CollectionModel<RepresentationModel<?>>> get() {
		final var elements = this.invitationRepository.getAll();
		return this.sirenMapper.map(elements);
	}

	@GetMapping(path = "/{id}/guests")
	public Mono<CollectionModel<?>> getGuests(@PathVariable String id) {
		final var invitationId = this.sirenMapper.map(id);
		final var element = this.invitationRepository.get(invitationId)
				// If Invitation not found
				.switchIfEmpty(Mono.error(ElementNotFoundException.builder().elementId(id)
						.functionalErrorCode("invitation_not_found").build()));
		return element.map(x -> this.sirenMapper.mapGuestStati(x)).flatMap(x -> x);
	}

	@GetMapping(path = "/{id}")
	public Mono<RepresentationModel<?>> self(@PathVariable String id) {
		final var invitationId = this.sirenMapper.map(id);
		final var element = this.invitationRepository.get(invitationId)
				// If Invitation not found
				.switchIfEmpty(Mono.error(ElementNotFoundException.builder().elementId(id)
						.functionalErrorCode("invitation_not_found").build()));
		return this.sirenMapper.map(element);
	}

	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Mono<RepresentationModel<?>> update(@RequestBody Mono<InvitationDTO> invitationDTO,
			@PathVariable String id) {
		final var invitationId = this.sirenMapper.map(id);
		;
		final var invitation = invitationDTO.flatMap(resource -> {
			final var dto = this.sirenMapper.map(resource);
			if (!invitationId.equals(dto.getId())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			}
			return this.invitationRepository.store(dto);
		});
		return this.sirenMapper.map(invitation);
	}
}
