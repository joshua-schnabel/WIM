package de.joshuaschnabel.wem.infrastructur.presentation.rest;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Condition;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.Affordance;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import de.joshuaschnabel.wem.domain.guest.Guest;
import de.joshuaschnabel.wem.domain.guest.GuestId;
import de.joshuaschnabel.wem.domain.guest.GuestRepository;
import de.joshuaschnabel.wem.domain.guest.GuestType;
import de.joshuaschnabel.wem.domain.guest.Name;
import de.joshuaschnabel.wem.infrastructur.presentation.rest.controllers.GuestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag("ComponentTest")
@ExtendWith(MockitoExtension.class)
class GuestControllerTest {

	private static MediaType sirenMediaType = MediaType
			.parseMediaTypes("application/vnd.siren+json").get(0);

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

		assertThat(allGuests.getContent()).hasSize(2);
		assertThat(allGuests.getLinks()).areAtLeastOne(this.hasLink("self", "/api/guests/"));
		assertThat(allGuests.getContent()).first() //
			.extracting(EntityModel::getLinks, as(InstanceOfAssertFactories.iterable(Link.class)))
			.areAtLeastOne(this.hasLink("self","/api/guests/nbqwy3dpgezdgmjs"))
			.first()
			.extracting(Link::getAffordances, as(InstanceOfAssertFactories.iterable(Affordance.class)))
			.areAtLeastOne(this.hasAffordance("updateGuest","/api/guests/nbqwy3dpgezdgmjs",HttpMethod.PUT))
			.areAtLeastOne(this.hasAffordance("deleteOne","/api/guests/nbqwy3dpgezdgmjs",HttpMethod.DELETE));
	}

	@Test
	@DisplayName("Test delete guest endpoint")
	void deleteOneGuests() {
		final Mono<Void> monoVoid = Mono.empty();
		when(this.repo.remove(Mockito.any(GuestId.class))).thenReturn(monoVoid);
		final var x = this.guestController.deleteOne("nbqwy3dpgezdgmjs")
				.block();
		System.out.println(x);
		verify(this.repo, times(1)).remove(any(GuestId.class));
	}

	@Test
	@DisplayName("Test findOne guest endpoint")
	void findOneGuests() {
		when(this.repo.get(Mockito.argThat(x -> x.get().equals("nbqwy3dpgezdgmjs"))))
			.thenReturn(Mono.just(GUEST1));
		final var guest = this.guestController.findOne("nbqwy3dpgezdgmjs").block();
		assertThat(guest.getContent().getId()).isEqualTo("nbqwy3dpgezdgmjs");
		assertThat(guest.getContent().getFirstname()).isEqualTo("Hans");
		assertThat(guest.getContent().getGuestType()).isEqualTo("PrimaryGuest");
		assertThat(guest.getLinks())
			.areAtLeastOne(this.hasLink("self", "/api/guests/nbqwy3dpgezdgmjs"))
			.areAtLeastOne(this.hasLink("guests", "/api/guests/"))
			.first()
			.extracting(Link::getAffordances, as(InstanceOfAssertFactories.iterable(Affordance.class)))
			.areAtLeastOne(this.hasAffordance("updateGuest","/api/guests/nbqwy3dpgezdgmjs",HttpMethod.PUT))
			.areAtLeastOne(this.hasAffordance("deleteOne","/api/guests/nbqwy3dpgezdgmjs",HttpMethod.DELETE));
	}

	private Condition<? super Affordance> hasAffordance(String rel, String href,
			HttpMethod method) {
		return new Condition<Affordance>(x -> {
			final var affordanceModel = x.getAffordanceModel(sirenMediaType);
			return affordanceModel.getURI().equals(href)
					&& affordanceModel.getLink().hasRel(rel)
					&& affordanceModel.getHttpMethod().equals(method);
		}, "link with rel " + rel + " and href " + href);
	}

	private Condition<? super Link> hasLink(String rel, String href) {
		return new Condition<Link>(
				link -> link.hasRel(rel) && link.getHref().equals(href),
				"link with rel " + rel + " and href " + href);
	}

	@BeforeEach
	void init() {
		Mockito.reset(this.repo);
	}

}
