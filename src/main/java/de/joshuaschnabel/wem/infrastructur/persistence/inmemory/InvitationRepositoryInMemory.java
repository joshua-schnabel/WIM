package de.joshuaschnabel.wem.infrastructur.persistence.inmemory;

import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import de.joshuaschnabel.wem.domain.invitation.Invitation;
import de.joshuaschnabel.wem.domain.invitation.InvitationCode;
import de.joshuaschnabel.wem.domain.invitation.InvitationId;
import de.joshuaschnabel.wem.domain.invitation.InvitationRepository;
import de.joshuaschnabel.wem.domain.invitation.SpecialRequest;
import de.joshuaschnabel.wem.domain.invitation.SpecialRequest.SpecialRequestId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Profile(value = "inMemoryDb")
@Component
public class InvitationRepositoryInMemory implements InvitationRepository {

    private final Map<InvitationId, Invitation> map = new HashMap<>();

    public InvitationRepositoryInMemory() {
        final var id1 = InvitationId.getNewId();
        final var sp1 =
                SpecialRequest.builder().id(new SpecialRequestId(1234)).request("Kannst du einen Kuchen mitbringen?").build();
        final var id2 = InvitationId.getNewId();
        final var sp2 = SpecialRequest.builder().id(new SpecialRequestId(1212))
                .request("Kannst du einen anderen Kuchen mitbringen?").build();
        var i1 = Invitation.builder().id(id1).invitationCode(InvitationCode.of("code1xx")).specialRequest(sp1).build();
        var i2 = Invitation.builder().id(id2).invitationCode(InvitationCode.of("code2xx")).specialRequest(sp2).build();
        this.map.put(id1, i1);
        this.map.put(id2, i2);
    }

    @Override
    public Mono<Invitation> get(InvitationId identity) {
        return Mono.justOrEmpty(this.map.get(identity));
    }

    @Override
    public Flux<Invitation> getAll() {
        return Flux.fromIterable(this.map.values());
    }

    @Override
    public Mono<Boolean> remove(Invitation aggregate) {
        return Mono.create(callback -> {
            final var result = this.map.values().removeIf(aggregate::equals);
            callback.success(result);
        });
    }

    @Override
    public Mono<Boolean> remove(InvitationId identity) {
        return Mono.create(callback -> {
            final var result = this.map.remove(identity) != null;
            callback.success(result);
        });
    }

    @Override
    public Mono<Invitation> store(Invitation agregate) {
        return Mono.create(callback -> {
            callback.success(this.map.put(agregate.getId(), agregate));
        });
    }

}
