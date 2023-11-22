package de.joshuaschnabel.wim.infrastructur.persistence.inmemory;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import de.joshuaschnabel.wim.domain.guest.Guest;
import de.joshuaschnabel.wim.domain.guest.GuestId;
import de.joshuaschnabel.wim.domain.guest.GuestName;
import de.joshuaschnabel.wim.domain.guest.GuestName.FirstName;
import de.joshuaschnabel.wim.domain.guest.GuestName.LastName;
import de.joshuaschnabel.wim.domain.guest.GuestRepository;
import de.joshuaschnabel.wim.domain.guest.GuestType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Profile(value = "inMemoryDb")
@Component
public class GuestRepositoryInMemory implements GuestRepository {

	private final Map<GuestId, Guest> map = new HashMap<>();

	public GuestRepositoryInMemory() {
		final var id1 = GuestId.getNewId();
		final var id2 = GuestId.getNewId();
		final var id3 = GuestId.getNewId();
		this.map.put(id1, Guest.builder().id(id1).name(GuestName.of(new FirstName("John"), new LastName("Doe")))
				.type(GuestType.PrimaryGuest).build());
		this.map.put(id2, Guest.builder().id(id2).name(GuestName.of(new FirstName("Jane"), new LastName("Doe")))
				.type(GuestType.PrimaryGuest).build());
		this.map.put(id3, Guest.builder().id(id3).name(GuestName.of(new FirstName("Max"), new LastName("Mustermann")))
				.type(GuestType.PrimaryGuest).build());
	}

	@Override
	public Mono<Guest> get(GuestId identity) {
		return Mono.justOrEmpty(this.map.get(identity));
	}

	@Override
	public Flux<Guest> getAll() {
		return Flux.fromIterable(this.map.values());
	}

	@Override
	public Mono<Boolean> remove(Guest aggregate) {
		return Mono.create(callback -> {
			final var result = this.map.remove(aggregate.getId()) != null;
			callback.success(result);
		});
	}

	@Override
	public Mono<Boolean> remove(GuestId identity) {
		return Mono.create(callback -> {
			final var result = this.map.remove(identity) != null;
			callback.success(result);
		});
	}

	@Override
	public Mono<Guest> store(Guest agregate) {
		return Mono.create(callback -> {
			this.map.put(agregate.getId(), agregate);
			callback.success(this.map.get(agregate.getId()));
		});
	}

}
