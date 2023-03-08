package de.joshuaschnabel.wem.domain.guest;

import de.joshuaschnabel.wem.domain.ddd.objects.Aggregate;
import de.joshuaschnabel.wem.domain.ddd.objects.AggregateId;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class GuestStatus extends Aggregate<GuestStatus.GuestStatusId> {

    public static class GuestStatusId extends AggregateId<Integer> {

        public GuestStatusId(Integer value) {
            super(value);
        }
    }

    private final GuestStatusId id;

    private GuestId guest;

    @Builder.Default
    private Boolean accepted = false;
}
