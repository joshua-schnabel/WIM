package de.joshuaschnabel.wim.infrastructur.presentation.rest.controllers;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import de.joshuaschnabel.wim.domain.guest.GuestRepository;
import de.joshuaschnabel.wim.infrastructur.presentation.rest.error.ElementNotFoundException;
import de.joshuaschnabel.wim.infrastructur.presentation.rest.mapper.GuestSirenMapper;
import de.joshuaschnabel.wim.infrastructur.presentation.rest.model.GuestMapper;
import de.joshuaschnabel.wim.infrastructur.presentation.rest.model.dto.GuestDTO;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "api/guests")
public class GuestController {

	private GuestMapper mapper = Mappers.getMapper(GuestMapper.class);

	@Autowired
	private GuestRepository guestRepository;

	private final GuestSirenMapper guestSirenMapper = new GuestSirenMapper();

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(path = "/")
	public Mono<CollectionModel<RepresentationModel<?>>> all() {
		final var elements = this.guestRepository.getAll();
		return this.guestSirenMapper.map(elements);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Mono<RepresentationModel<?>> createGuest(@RequestBody Mono<GuestDTO> guestDTO) {
		return guestDTO.flatMap(resource -> {
			final var dto = this.guestSirenMapper.map(resource);
			dto.initializeNew();
			final var object = this.guestRepository.store(dto);
			return this.guestSirenMapper.map(object);
		});
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping(path = "/{id}")
	public Mono<ResponseEntity<?>> deleteGuest(@PathVariable String id) {
		final var guestId = this.mapper.StringToGuestId(id);
		return this.guestRepository.remove(guestId).map(result -> {
			if (result) {
				return ResponseEntity.ok().build();
			}
			return ResponseEntity.notFound().build();
		});
	}

	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_GUEST')")
	@GetMapping(path = "/{id}")
	public Mono<RepresentationModel<?>> findGuest(@PathVariable String id) {
		final var guestId = this.mapper.StringToGuestId(id);
		final var element = this.guestRepository.get(guestId)
				// If Guest not found
				.switchIfEmpty(Mono.error(ElementNotFoundException.builder().elementId(id)
						.functionalErrorCode("guest_not_found").build()));
		return this.guestSirenMapper.map(element);
	}

	@Transactional
	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_GUEST')")
	@PatchMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Mono<RepresentationModel<?>> updateGuest(@RequestBody Mono<GuestDTO> guestDTO, @PathVariable String id) {
		final var guestId = this.mapper.StringToGuestId(id);
		final var guest = guestDTO.flatMap(resource -> {
			var storedGuets = this.guestRepository.get(guestId).map(x -> {
				final var dto = this.guestSirenMapper.map(resource);
				x.setName(dto.getName().get());
				return x;
			}).map(x -> {
				if (!guestId.equals(x.getId())) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
				}
				return x;
			}).map(x -> this.guestRepository.store(x));
			return storedGuets.flatMap(x -> x);
		});
		return this.guestSirenMapper.map(guest);
	}
}
