package de.joshuaschnabel.wim.infrastructur.persistence.R2DBC;

import java.util.List;
import java.util.function.Function;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import de.joshuaschnabel.wim.domain.invitation.Invitation;
import de.joshuaschnabel.wim.domain.invitation.InvitationCode;
import de.joshuaschnabel.wim.domain.invitation.InvitationId;
import de.joshuaschnabel.wim.domain.invitation.InvitationRepository;
import de.joshuaschnabel.wim.infrastructur.persistence.R2DBC.model.InvitationMapper;
import de.joshuaschnabel.wim.infrastructur.persistence.R2DBC.model.pojo.GuestStatusPojo;
import de.joshuaschnabel.wim.infrastructur.persistence.R2DBC.model.pojo.InvitationPojo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Component
@Profile({ "h2-prod", "h2", "mysql" })
public class InvitationR2DBCRepository implements InvitationRepository {

	@Autowired
	private InvitationDBRepository invRepo;

	@Autowired
	private GuestStatusDBRepository gstRepo;

	private InvitationMapper mapper = Mappers.getMapper(InvitationMapper.class);

	@Override
	public Mono<Invitation> get(InvitationId identity) {
		var id = this.mapper.invitationIdTOString(identity);
		var element = this.invRepo.findById(id);
		var stati = this.gstRepo.findByInvitationId(id).collectList();
		return Mono.zip(element, stati).map(this.mergeElementAndStati()).map(this.mapper::invitationPojoTOInvitation);
	}

	@Override
	public Flux<Invitation> getAll() {
		var elements = this.invRepo.findAll().map(this.getStati()).flatMap(x -> x);
		return elements.map(this.mergeElementAndStati()).map(this.mapper::invitationPojoTOInvitation);
	}

	@Override
	public Mono<Invitation> getByCode(InvitationCode invitationCode) {
		var code = this.mapper.invitationCodeTOString(invitationCode);
		var element = this.invRepo.findByCode(code).map(this.getStati()).flatMap(mono -> mono);
		return element.map(this.mergeElementAndStati()).map(this.mapper::invitationPojoTOInvitation);
	}

	private Function<? super InvitationPojo, ? extends Mono<Tuple2<InvitationPojo, List<GuestStatusPojo>>>> getStati() {
		return x -> {
			var id = x.getId();
			var stati = this.gstRepo.findByInvitationId(id).collectList();
			return Mono.zip(Mono.just(x), stati);
		};
	}

	private Function<? super Tuple2<InvitationPojo, List<GuestStatusPojo>>, ? extends InvitationPojo> mergeElementAndStati() {
		return x -> {
			x.getT1().setGuestStati(x.getT2());
			return x.getT1();
		};
	}

	@Override
	@Transactional
	public Mono<Boolean> remove(Invitation aggregate) {
		var id = this.mapper.invitationIdTOString(aggregate.getId());
		var statiDeleteStatus = this.gstRepo.deleteByInvitationId(id);
		var deleteStatus = this.invRepo.deleteById(id);
		return deleteStatus.then(statiDeleteStatus).thenReturn(true).onErrorResume(throwable -> Mono.just(false));
	}

	@Override
	@Transactional
	public Mono<Boolean> remove(InvitationId identity) {
		var id = this.mapper.invitationIdTOString(identity);
		var statiDeleteStatus = this.gstRepo.deleteByInvitationId(id);
		var deleteStatus = this.invRepo.deleteById(id);
		return deleteStatus.then(statiDeleteStatus).thenReturn(true).onErrorResume(throwable -> Mono.just(false));
	}

	@Override
	@Transactional
	public Mono<Invitation> store(Invitation agregate) {
		var element = this.mapper.invitationTOinvitationPojo(agregate);
		var statiList = Flux.fromIterable(element.getGuestStati()).map(x -> {
			x.setInvitationId(element.getId());
			return x;
		}).map(x -> this.gstRepo.save(x)).flatMap(mono -> mono).collectList();
		var saveResult = this.invRepo.save(element);
		return Mono.zip(saveResult, statiList).map(this.mergeElementAndStati())
				.map(this.mapper::invitationPojoTOInvitation);
	}
}
