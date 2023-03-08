package de.joshuaschnabel.wem.infrastructur.presentation.rest.model;

import static org.assertj.core.api.Assertions.assertThat;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import de.joshuaschnabel.wem.domain.guest.Guest;

@Tag("UnitTest")
@DisplayName("Test DTO to DomainObject with GuestMapper")
class GuestDtoToDomainTest {

    @Test
    @DisplayName("Test full DTO to DomainObject mapping")
    void fullDTO() {
        final var guestDto = GuestDTO.of("abcdefgh2345abcd", "Hans", "Wurst", "PrimaryGuest");
        final var guest = GuestMapper.mapDtoToGuest.apply(guestDto);
        assertThat(guest).has(this.hasId("abcdefgh2345abcd"));
        assertThat(guest).has(this.hasName("Hans", "Wurst"));
        assertThat(guest).has(this.hasType("PrimaryGuest"));
    }

    private Condition<? super Guest> hasId(String id) {
        return new Condition<Guest>(m -> m.getId().get().equals(id), "id " + id);
    }

    private Condition<? super Guest> hasName(String first, String last) {
        return new Condition<Guest>(
                m -> m.getName().map(name -> name.getFirstname().equals(first) && name.getLastname().equals(last)).get(),
                "name " + first + " " + last);
    }

    private Condition<? super Guest> hasNoName() {
        return new Condition<Guest>(m -> m.getName().isEmpty(), "no id");
    }

    private Condition<? super Guest> hasType(String type) {
        return new Condition<Guest>(m -> m.getType().name().equals(type), "type " + type);
    }

    @Test
    @DisplayName("Test missing Name DTO to DomainObject mapping")
    void missingNameDTO() {
        final var guestDto = GuestDTO.of("abcdefgh2345abcd", null, null, "PrimaryGuest");
        final var guest = GuestMapper.mapDtoToGuest.apply(guestDto);
        assertThat(guest).has(this.hasId("abcdefgh2345abcd"));
        assertThat(guest).has(this.hasNoName());
        assertThat(guest).has(this.hasType("PrimaryGuest"));
    }

}
