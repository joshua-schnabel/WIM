package de.joshuaschnabel.wem.domain.guest;

import de.joshuaschnabel.wem.domain.ddd.generators.RandomStringIdGenerator;
import de.joshuaschnabel.wem.domain.ddd.objects.AggregateRootId;

public class GuestId extends AggregateRootId<String> {
    public static class GuestIdGenerator extends RandomStringIdGenerator<GuestId> {
        @Override
        public GuestId generate() {
            return new GuestId(this.generateID());
        }
    }

    private static GuestIdGenerator generator = new GuestIdGenerator();

    public static GuestId getNewId() {
        return generator.generate();
    }

    public static GuestId of(String id) {
        id = id.toLowerCase();
        if (!generator.validate(id)) {
            throw new IllegalArgumentException("Id should be " + generator.getSpecification());
        }
        return new GuestId(id);
    }

    protected GuestId(String value) {
        super(value);
    }

    @Override
    public String toString() {
        return "GuestId [id=" + this.get() + "]";
    }

}
