package de.joshuaschnabel.wem.domain.invitation;

import de.joshuaschnabel.wem.domain.ddd.generators.RandomStringIdGenerator;
import de.joshuaschnabel.wem.domain.ddd.objects.AggregateRootId;

public class InvitationId extends AggregateRootId<String> {

    public static class InvitationIdGenerator extends RandomStringIdGenerator<InvitationId> {
        @Override
        public InvitationId generate() {
            return new InvitationId(this.generateID());
        }
    }

    private static InvitationIdGenerator generator = new InvitationIdGenerator();

    public static InvitationId getNewId() {
        return generator.generate();
    }

    public static InvitationId of(String id) {
        if (!generator.validate(id)) {
            throw new IllegalArgumentException("Id should be " + generator.getSpecification());
        }
        return new InvitationId(id);
    }

    protected InvitationId(String value) {
        super(value);
    }

    @Override
    public String toString() {
        return "GuestId [id()=" + this.get() + "]";
    }

}
