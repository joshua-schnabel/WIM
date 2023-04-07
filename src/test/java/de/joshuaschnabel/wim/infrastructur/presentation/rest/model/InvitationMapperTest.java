package de.joshuaschnabel.wim.infrastructur.presentation.rest.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import de.joshuaschnabel.wim.infrastructur.presentation.rest.model.dto.GuestStatusDTO;
import de.joshuaschnabel.wim.infrastructur.presentation.rest.model.dto.InvitationDTO;

class InvitationMapperTest {

	private InvitationMapper invitationMapper = Mappers.getMapper(InvitationMapper.class);

	@Test
	@DisplayName("Test full DTO to DomainObject mapping both ways")
	void testInvitationMapper() {
		var invitationDTO = InvitationDTO.builder().id("MZSXOZTFO4QGMIBA").code("test123")
				.specialRequest("Kannst du Kuchen bringen?").specialRequestAccepted(false).specialRequestAnswer("NEIN!")
				.build();

		var result = this.invitationMapper.invitationDTOTOInvitation(invitationDTO);

		assertThat(result.getId().get()).isEqualToIgnoringCase("MZSXOZTFO4QGMIBA");
		assertThat(result.getCode().getValue()).isEqualTo("test123");

		assertThat(result.getSpecialRequest().getAccepted().getValue()).isFalse();
		assertThat(result.getSpecialRequest().getRequest().getValue()).isEqualTo("Kannst du Kuchen bringen?");
		assertThat(result.getSpecialRequest().getAnswer().getValue()).isEqualTo("NEIN!");

		var resultBack = this.invitationMapper.invitationTOinvitationDTO(result);

		assertThat(resultBack.getId()).isEqualToIgnoringCase("MZSXOZTFO4QGMIBA");
		assertThat(resultBack.getCode()).isEqualToIgnoringCase("test123");
		assertThat(resultBack.getSpecialRequest()).isEqualToIgnoringCase("Kannst du Kuchen bringen?");
		assertThat(resultBack.getSpecialRequestAnswer()).isEqualToIgnoringCase("NEIN!");
		assertThat(resultBack.getSpecialRequestAccepted()).isFalse();
		assertThat(resultBack.getConfirmedGuestsCount()).isEqualTo(0);
		assertThat(resultBack.getAssignedGuestsCount()).isEqualTo(0);
	}

	@Test
	@DisplayName("Test full DTO to DomainObject mapping both ways")
	void testInvitationStatusMapper() {
		var guestStatusDTO2 = GuestStatusDTO.builder().guestId("JBSWY3DPGEZDGNB2").accepted(true).build();
		var guestStatusDTO3 = GuestStatusDTO.builder().guestId("JHSWY3DPGEZDGNB3").accepted(true).build();
		var guestStatusDTO4 = GuestStatusDTO.builder().guestId("JISWY3DPGEZDGNB4").accepted(true).build();

	}

}
