package de.joshuaschnabel.wim.infrastructur.presentation.rest.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import de.joshuaschnabel.wim.domain.guest.GuestType;
import de.joshuaschnabel.wim.infrastructur.presentation.rest.model.dto.GuestDTO;
import de.joshuaschnabel.wim.infrastructur.presentation.rest.model.dto.GuestDTO.GuestNameDTO;

class GuestMapperTest {

	private GuestMapper guestMapper = Mappers.getMapper(GuestMapper.class);

	@Test
	@DisplayName("Test full DTO to DomainObject mapping both ways")
	void test() {
		var guestDTO = GuestDTO.builder().id("MZSXOZTFO4QGMIBA")
				.name(GuestNameDTO.builder().firstname("Hans").lastname("Wurst").build()).guestType("Child").build();
		var result = this.guestMapper.guestDTOTOguest(guestDTO);
		assertThat(result.getId().get()).isEqualToIgnoringCase("MZSXOZTFO4QGMIBA");
		assertThat(result.getName()).isPresent().hasValueSatisfying(x -> {
			assertThat(x.getFirstname()).isEqualTo("Hans");
			assertThat(x.getLastname()).isEqualTo("Wurst");
		});
		assertThat(result.getType()).isEqualTo(GuestType.Child);

		var back = this.guestMapper.guestTOguestDTO(result);

		assertThat(back.getId()).isEqualToIgnoringCase("MZSXOZTFO4QGMIBA");
		assertThat(back.getName()).satisfies(x -> {
			assertThat(x.getFirstname()).isEqualTo("Hans");
			assertThat(x.getLastname()).isEqualTo("Wurst");
		});
		assertThat(back.getGuestType()).isEqualTo("Child");
	}

}
