package de.joshuaschnabel.wim.infrastructur.persistence.R2DBC;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import de.joshuaschnabel.wim.infrastructur.persistence.R2DBC.model.pojo.GuestStatusPojo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

interface GuestStatusDBRepository extends R2dbcRepository<GuestStatusPojo, Integer> {

	@Query("DELETE FROM guestStati s WHERE s.invitationId=:invitationId")
	Mono<Void> deleteByInvitationId(String invitationId);

	@Query("SELECT * FROM guestStati WHERE invitationId = :invitationId")
	Flux<GuestStatusPojo> findByInvitationId(String invitationId);

}
