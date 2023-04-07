package de.joshuaschnabel.wim.infrastructur.persistence.inmemory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import de.joshuaschnabel.wim.domain.guest.GuestRepository;
import de.joshuaschnabel.wim.domain.guest.GuestStatus;
import de.joshuaschnabel.wim.domain.guest.GuestStatus.GuestStatusAccepted;
import de.joshuaschnabel.wim.domain.guest.GuestStatus.GuestStatusId;
import de.joshuaschnabel.wim.domain.invitation.Invitation;
import de.joshuaschnabel.wim.domain.invitation.Invitation.InvitationName;
import de.joshuaschnabel.wim.domain.invitation.InvitationCode;
import de.joshuaschnabel.wim.domain.invitation.InvitationId;
import de.joshuaschnabel.wim.domain.invitation.InvitationRepository;
import de.joshuaschnabel.wim.domain.invitation.InvitationStatus;
import de.joshuaschnabel.wim.domain.invitation.SpecialRequest;
import de.joshuaschnabel.wim.domain.invitation.SpecialRequest.Request;
import de.joshuaschnabel.wim.domain.invitation.SpecialRequest.RequestAnser;
import de.joshuaschnabel.wim.domain.invitation.SpecialRequest.RequestStatus;
import de.joshuaschnabel.wim.domain.invitation.SpecialRequest.SpecialRequestId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Profile(value = "inMemoryDb")
@Component
public class InvitationRepositoryInMemory implements InvitationRepository {

	private final Map<InvitationId, Invitation> map = new HashMap<>();

	public InvitationRepositoryInMemory(@Autowired GuestRepository guestRepository) {
		var guests = guestRepository.getAll().collectList().block();
		final var id1 = InvitationId.getNewId();
		final var sp1 = SpecialRequest.builder().id(new SpecialRequestId(1234))
				.request(new Request("Kannst du einen Kuchen mitbringen?")).accepted(RequestStatus.YES)
				.answer(new RequestAnser("Mamorkuchen")).build();
		final var id2 = InvitationId.getNewId();
		final var sp2 = SpecialRequest.builder().id(new SpecialRequestId(1212))
				.request(new Request("Kannst du einen anderen Kuchen mitbringen?")).accepted(RequestStatus.NO).build();
		var status1 = new GuestStatus(new GuestStatusId(1234), guests.get(0).getId(), GuestStatusAccepted.YES);
		var status2 = new GuestStatus(new GuestStatusId(1231), guests.get(1).getId(), GuestStatusAccepted.YES);
		var status3 = new GuestStatus(new GuestStatusId(3412), guests.get(2).getId(), GuestStatusAccepted.NO);
		var i1 = Invitation.builder().id(id1).code(new InvitationCode("1234abcd"))
				.name(new InvitationName("Familie Müller")).specialRequest(sp1)
				.guestStati(new ArrayList<GuestStatus>(List.of(status1))).status(InvitationStatus.OPENED).build();
		var i2 = Invitation.builder().id(id2).code(new InvitationCode("4321abcd"))
				.name(new InvitationName("Familie Hering")).specialRequest(sp2)
				.guestStati(new ArrayList<GuestStatus>(List.of(status2, status3))).status(InvitationStatus.ANSWERED)
				.build();
		this.map.put(id1, i1);
		this.map.put(id2, i2);
	}

	@Override
	public Mono<Invitation> get(InvitationId identity) {
		return Mono.justOrEmpty(this.map.get(identity));
	}

	@Override
	public Flux<Invitation> getAll() {
		return Flux.fromIterable(this.map.values());
	}

	@Override
	public Mono<Invitation> getByCode(InvitationCode code) {
		return Mono.create(callback -> {
			var x = this.map.values().stream().filter(y -> y.getCode().equals(code)).findFirst();
			if (x.isPresent()) {
				callback.success(x.get());
			} else {
				callback.success();
			}
		});
	}

	@Override
	public Mono<Boolean> remove(Invitation aggregate) {
		return Mono.create(callback -> {
			final var result = this.map.remove(aggregate.getId()) != null;
			callback.success(result);
		});
	}

	@Override
	public Mono<Boolean> remove(InvitationId identity) {
		return Mono.create(callback -> {
			final var result = this.map.remove(identity) != null;
			callback.success(result);
		});
	}

	@Override
	public Mono<Invitation> store(Invitation agregate) {
		return Mono.create(callback -> {
			this.map.put(agregate.getId(), agregate);
			callback.success(this.map.get(agregate.getId()));
		});
	}

}
