package de.joshuaschnabel.wem.infrastructur.presentation.rest;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.config.HypermediaWebTestClientConfigurer;
import org.springframework.hateoas.server.core.TypeReferences;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import de.joshuaschnabel.wem.domain.guest.GuestId;
import de.joshuaschnabel.wem.domain.guest.GuestRepository;
import de.joshuaschnabel.wem.infrastructur.presentation.rest.model.dto.GuestDTO;

@SpringBootTest
@AutoConfigureWebTestClient
@ActiveProfiles("inMemoryDb")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GuestControllerApiTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private HypermediaWebTestClientConfigurer configurer;

    private GuestId userId;

    @BeforeAll
    void getUserId() {
        this.userId = this.guestRepository.getAll().blockFirst().getId();
    }

    @BeforeAll
    void setupWebTestClient() {
        this.webTestClient = this.webTestClient.mutateWith(this.configurer);
    }

    @Test
    public void testGetData() {
        this.webTestClient.get().uri("/api/guests/fsw37u7w5ioj6mzg").exchange().expectStatus().isNotFound();
        this.webTestClient.get().uri("/api/guests/" + this.userId.get()).exchange()//
                .expectStatus().isOk()//
                .expectHeader().contentType("application/vnd.siren+json") //
                .expectBody(new TypeReferences.EntityModelType<GuestDTO>() {}).value(model -> {
                    var dto = model.getContent();
                    assertThat(dto.getId()).isEqualTo(this.userId.get());
                    assertThat(model.getLink("guests").get().getHref()).isEqualTo("api/guests/");
                    assertThat(model.getLink("self").get().getHref()).isEqualTo("api/guests/" + this.userId.get());
                });
    }
}
