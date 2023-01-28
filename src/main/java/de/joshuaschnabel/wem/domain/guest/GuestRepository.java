package de.joshuaschnabel.wem.domain.guest;

import de.joshuaschnabel.wem.domain.ddd.repositories.Repository;
import reactor.core.publisher.Flux;

public interface GuestRepository extends Repository<GuestId, Guest> {

  public Flux<Guest> getAll();


}
