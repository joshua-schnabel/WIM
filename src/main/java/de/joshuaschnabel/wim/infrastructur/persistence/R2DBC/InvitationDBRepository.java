package de.joshuaschnabel.wim.infrastructur.persistence.R2DBC;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import de.joshuaschnabel.wim.infrastructur.persistence.R2DBC.model.pojo.InvitationPojo;
import reactor.core.publisher.Mono;

interface InvitationDBRepository extends R2dbcRepository<InvitationPojo, String> {

	@Query("SELECT * FROM invitations WHERE code = :code")
	Mono<InvitationPojo> findByCode(String code);

}
