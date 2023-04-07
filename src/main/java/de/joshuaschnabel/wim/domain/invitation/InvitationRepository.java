package de.joshuaschnabel.wim.domain.invitation;

import de.joshuaschnabel.wim.domain.ddd.repositories.Repository;
import reactor.core.publisher.Mono;

public interface InvitationRepository extends Repository<InvitationId, Invitation> {

	Mono<Invitation> getByCode(InvitationCode code);

}
