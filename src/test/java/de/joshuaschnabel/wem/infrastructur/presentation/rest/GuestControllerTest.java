package de.joshuaschnabel.wem.infrastructur.presentation.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import de.joshuaschnabel.wem.domain.guest.Guest;
import de.joshuaschnabel.wem.domain.guest.GuestId;
import de.joshuaschnabel.wem.domain.guest.GuestRepository;
import de.joshuaschnabel.wem.domain.guest.GuestType;
import de.joshuaschnabel.wem.domain.guest.Name;
import de.joshuaschnabel.wem.infrastructur.presentation.rest.controllers.GuestController;
import reactor.core.publisher.Flux;

@Tag("ComponentTest")
@ExtendWith(MockitoExtension.class)
class GuestControllerTest {

	private static Guest GUEST1 = Guest.builder()
			.id(GuestId.of("nbqwy3dpgezdgmjs")).type(GuestType.PrimaryGuest)
			.invitation(Optional.empty())
			.name(Optional.of(Name.of("Hans", "Wurst"))).build();

	private static Guest GUEST2 = Guest.builder()
			.id(GuestId.of("mn3wk5ztmv5dqnzs")).type(GuestType.PrimaryGuest)
			.invitation(Optional.empty())
			.name(Optional.of(Name.of("Anita", "Käse"))).build();

	@Mock
	private GuestRepository repo;

	@InjectMocks
	private GuestController guestController;

	@Test
	@DisplayName("Test all guests endpoint")
	void allGuests() {
		when(this.repo.getAll()).thenReturn(Flux.fromIterable(List.of(GUEST1,GUEST2)));

		final var allGuests = this.guestController.all().block();

		System.out.println(allGuests.getLinks());

		assertThat(allGuests.getContent()).hasSize(2);
		//assertThat(allGuests.getLinks()).hasSize(1).allMatch(link -> link.hasRel("self") && link.getHref());
	}

	@BeforeEach
	void init() {
		Mockito.reset(this.repo);
	}

}
