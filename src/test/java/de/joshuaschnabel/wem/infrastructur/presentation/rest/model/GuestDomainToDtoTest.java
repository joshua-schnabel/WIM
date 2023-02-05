package de.joshuaschnabel.wem.infrastructur.presentation.rest.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Optional;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import de.joshuaschnabel.wem.domain.guest.Guest;
import de.joshuaschnabel.wem.domain.guest.GuestId;
import de.joshuaschnabel.wem.domain.guest.GuestType;
import de.joshuaschnabel.wem.domain.guest.Name;
import de.joshuaschnabel.wem.domain.invitation.InvitationId;

@Tag("UnitTest")
@DisplayName("Test DomainObject to DTO with GuestMapper")
class GuestDomainToDtoTest {

	@Test
	@DisplayName("Test full DomainObject to DTO mapping")
	void fullDTO() {
		final var guest = Guest.builder().id(GuestId.of("abcdefgh2345abcd"))
				.name(Optional.of(Name.of("Hans", "Wurst")))
				.type(GuestType.PrimaryGuest)
				.invitation(Optional.of(InvitationId.of("testID"))).build();
		final var result = GuestMapper.mapToDTO.apply(guest);

		assertThat(result).has(this.hasId("abcdefgh2345abcd"));
		assertThat(result).has(this.hasName("Hans", "Wurst"));
		assertThat(result).has(this.hasType("PrimaryGuest"));
	}

	private Condition<? super GuestDTO> hasId(String id) {
		return new Condition<GuestDTO>(m -> m.getId().equals(id), "id " + id);
	}

	private Condition<GuestDTO> hasName(String first, String last) {
		return new Condition<GuestDTO>(
				m -> m.getFirstname().equalsIgnoreCase(first)
						&& m.getLastmame().equalsIgnoreCase(last),
				"name " + first + " " + last);
	}

	private Condition<GuestDTO> hasType(String type) {
		return new Condition<GuestDTO>(m -> m.getGuestType().equals(type),
				"type " + type);
	}

	@Test
	@DisplayName("Test missing name DomainObject to DTO mapping")
	void missingNameDTO() {
		final var guest = Guest.builder().id(GuestId.of("abcdefgh2345abcd"))
				.type(GuestType.PrimaryGuest)
				.invitation(Optional.of(InvitationId.of("efghabcd2345abcd")))
				.build();
		final var result = GuestMapper.mapToDTO.apply(guest);

		assertThat(result).has(this.hasId("abcdefgh2345abcd"));
		assertThat(result)
				.extracting(GuestDTO::getFirstname, GuestDTO::getLastmame)
				.containsExactly(null, null);
		assertThat(result).has(this.hasType("PrimaryGuest"));
	}

	@Test
	@DisplayName("Test missing type DomainObject to DTO mapping")
	void missingTypeDTO() {
		final var guest = Guest.builder().id(GuestId.of("abcdefgh2345abcd"))
				.name(Optional.of(Name.of("Hans", "Wurst")))
				.invitation(Optional.of(InvitationId.of("abcdefgh2345abcd")))
				.build();

		assertThatThrownBy(() -> {
			GuestMapper.mapToDTO.apply(guest);
		}).isInstanceOf(IllegalStateException.class)
				.hasMessage("Type not defind");
	}

}
