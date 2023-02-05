package de.joshuaschnabel.wem.infrastructur.presentation.rest.controllers;

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

import de.joshuaschnabel.wem.domain.guest.GuestRepository;
import de.joshuaschnabel.wem.infrastructur.presentation.rest.error.ElementNotFoundException;
import de.joshuaschnabel.wem.infrastructur.presentation.rest.hatos.GuestHatosDecorator;
import de.joshuaschnabel.wem.infrastructur.presentation.rest.model.GuestDTO;
import de.joshuaschnabel.wem.infrastructur.presentation.rest.model.GuestMapper;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "api/guests")
public class GuestController {

	@Autowired
	private GuestRepository guestRepository;

	private final GuestHatosDecorator hatosDecorator = new GuestHatosDecorator();

	@GetMapping(path = "/")
	public Mono<CollectionModel<EntityModel<GuestDTO>>> all() {
		final var elements = this.guestRepository.getAll()
				.map(GuestMapper.mapToDTO);
		return this.hatosDecorator.addListLinks(elements);
	}

	@DeleteMapping(path = "/{id}")
	public Mono<ResponseEntity<?>> deleteOne(@PathVariable String id) {
		final var guestId = GuestMapper.mapDtoIdToGuest.apply(id);
		return this.guestRepository.remove(guestId).map(x -> {
			System.out.println(x);
			return ResponseEntity.ok().build();
		});
	}

	@GetMapping(path = "/{id}")
	public Mono<EntityModel<GuestDTO>> findOne(@PathVariable String id) {
		final var guestId = GuestMapper.mapDtoIdToGuest.apply(id);
		final var element = this.guestRepository.get(guestId)
				// If Guest not found
				.switchIfEmpty(Mono.error(ElementNotFoundException.builder()
						.elementId(id).functionalErrorCode("guest_not_found")
						.build()))
				.map(GuestMapper.mapToDTO);
		return this.hatosDecorator.addLinks(element);
	}

	@PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Mono<EntityModel<GuestDTO>> newGuest(
			@RequestBody Mono<EntityModel<GuestDTO>> guestDTO) {
		return guestDTO.flatMap(resource -> {
			final var dto = GuestMapper.mapDtoToGuest
					.apply(resource.getContent());
			final var object = this.guestRepository.store(dto);
			final Mono<GuestDTO> result = object.map(GuestMapper.mapToDTO);
			return this.hatosDecorator.addLinks(result);
		});
	}

	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Mono<EntityModel<GuestDTO>> updateGuest(
			@RequestBody Mono<EntityModel<GuestDTO>> guestDTO,
			@PathVariable String id) {
		final var guestId = GuestMapper.mapDtoIdToGuest.apply(id);
		final var guest = guestDTO.flatMap(resource -> {
			final var dto = GuestMapper.mapDtoToGuest
					.apply(resource.getContent());
			if (!guestId.equals(dto.getId())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			}
			final var object = this.guestRepository.store(dto);
			return object.map(GuestMapper.mapToDTO);
		});
		return this.hatosDecorator.addLinks(guest);
	}
}
