package de.joshuaschnabel.wem.infrastructur.presentation.rest.controllers;

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
import de.joshuaschnabel.wem.domain.guest.Guest;
import de.joshuaschnabel.wem.domain.guest.GuestRepository;
import de.joshuaschnabel.wem.infrastructur.presentation.rest.error.ElementNotFoundException;
import de.joshuaschnabel.wem.infrastructur.presentation.rest.hatos.GuestHatosDecorator;
import de.joshuaschnabel.wem.infrastructur.presentation.rest.model.GuestMapper;
import de.joshuaschnabel.wem.infrastructur.presentation.rest.model.dto.GuestDTO;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "api/guests")
public class GuestController {

    private GuestMapper mapper = Mappers.getMapper(GuestMapper.class);

    @Autowired
    private GuestRepository guestRepository;

    private final GuestHatosDecorator hatosDecorator = new GuestHatosDecorator();

    @GetMapping(path = "/")
    public Mono<CollectionModel<EntityModel<GuestDTO>>> all() {
        final var elements = this.guestRepository.getAll().map(this.mapToGuest());
        return this.hatosDecorator.addListLinks(elements);
    }

    @DeleteMapping(path = "/{id}")
    public Mono<ResponseEntity<?>> deleteOne(@PathVariable String id) {
        final var guestId = this.mapper.StringToGuestId(id);
        return this.guestRepository.remove(guestId).map(result -> {
            if (result) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        });
    }

    @GetMapping(path = "/{id}")
    public Mono<EntityModel<GuestDTO>> findOne(@PathVariable String id) {
        final var guestId = this.mapper.StringToGuestId(id);
        final var element = this.guestRepository.get(guestId)
                // If Guest not found
                .switchIfEmpty(Mono
                        .error(ElementNotFoundException.builder().elementId(id).functionalErrorCode("guest_not_found").build()))
                .map(this.mapToGuest());
        return this.hatosDecorator.addLinks(element);
    }

    private Function<Guest, GuestDTO> mapToGuest() {
        return x -> this.mapper.guestTOguestDTO(x);
    }

    private Guest mapToGuest(EntityModel<GuestDTO> resource) {
        return this.mapper.guestDTOTOguest(resource.getContent());
    }

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<EntityModel<GuestDTO>> newGuest(@RequestBody Mono<EntityModel<GuestDTO>> guestDTO) {
        return guestDTO.flatMap(resource -> {
            final var dto = this.mapToGuest(resource);
            final var object = this.guestRepository.store(dto);
            return this.hatosDecorator.addLinks(object.map(this.mapToGuest()));
        });
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<EntityModel<GuestDTO>> updateGuest(@RequestBody Mono<EntityModel<GuestDTO>> guestDTO, @PathVariable String id) {
        final var guestId = this.mapper.StringToGuestId(id);
        final var guest = guestDTO.flatMap(resource -> {
            final var dto = this.mapToGuest(resource);
            if (!guestId.equals(dto.getId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            final var object = this.guestRepository.store(dto);
            return object.map(this.mapToGuest());
        });
        return this.hatosDecorator.addLinks(guest);
    }
}
