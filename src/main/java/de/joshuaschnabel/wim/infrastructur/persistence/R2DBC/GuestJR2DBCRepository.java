package de.joshuaschnabel.wim.infrastructur.persistence.R2DBC;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import de.joshuaschnabel.wim.domain.guest.Guest;
import de.joshuaschnabel.wim.domain.guest.GuestId;
import de.joshuaschnabel.wim.domain.guest.GuestRepository;
import de.joshuaschnabel.wim.infrastructur.persistence.R2DBC.model.GuestMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Profile(value = "h2")
public class GuestJR2DBCRepository implements GuestRepository {

	@Autowired
	private GuestDBRepository repo;

	private GuestMapper mapper = Mappers.getMapper(GuestMapper.class);

	@Override
	public Mono<Guest> get(GuestId identity) {
		var id = this.mapper.guestIdToString(identity);
		var element = this.repo.findById(id);
		return element.map(this.mapper::guestPojoTOGuest);
	}

	@Override
	public Flux<Guest> getAll() {
		return this.repo.findAll().map(this.mapper::guestPojoTOGuest);
	}

	@Override
	public Mono<Boolean> remove(Guest aggregate) {
		var id = this.mapper.guestIdToString(aggregate.getId());
		return this.repo.deleteById(id).thenReturn(true).onErrorReturn(false);
	}

	@Override
	public Mono<Boolean> remove(GuestId identity) {
		var id = this.mapper.guestIdToString(identity);
		return this.repo.deleteById(id).thenReturn(true).onErrorReturn(false);
	}

	@Override
	public Mono<Guest> store(Guest agregate) {
		var element = this.mapper.guestTOGuestPojo(agregate);
		return this.repo.save(element).map(this.mapper::guestPojoTOGuest);
	}

}
