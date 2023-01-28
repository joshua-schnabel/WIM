package de.joshuaschnabel.wem.infrastructur.persistence.inmemory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import de.joshuaschnabel.wem.domain.guest.Guest;
import de.joshuaschnabel.wem.domain.guest.GuestId;
import de.joshuaschnabel.wem.domain.guest.GuestRepository;
import de.joshuaschnabel.wem.domain.guest.GuestType;
import de.joshuaschnabel.wem.domain.guest.Name;
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
    this.map.put(id1, Guest.builder().id(id1).name(Optional.of(Name.of("John", "Doe")))
        .type(GuestType.PrimaryGuest).build());
    this.map.put(id2, Guest.builder().id(id2).name(Optional.of(Name.of("Jane", "Doe")))
        .type(GuestType.PrimaryGuest).build());
    this.map.put(id3, Guest.builder().id(id3).name(Optional.of(Name.of("Max", "Mustermann")))
        .type(GuestType.PrimaryGuest).build());
    System.out.println(this.map);
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
  public Mono<Void> remove(Guest aggregate) {
    return Mono.create(callback -> {
      this.map.values().removeIf(aggregate::equals);
      callback.success();
    });
  }

  @Override
  public Mono<Void> remove(GuestId identity) {
    return Mono.create(callback -> {
      this.map.remove(identity);
      callback.success();
    });
  }

  @Override
  public Mono<Guest> store(Guest agregate) {
    return Mono.create(callback -> {
      callback.success(this.map.put(agregate.getId(), agregate));
    });
  }

}
