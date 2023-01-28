package de.joshuaschnabel.wem.domain.guest.status;

import de.joshuaschnabel.wem.domain.ddd.objects.Aggregate;
import de.joshuaschnabel.wem.domain.ddd.objects.AggregateId;
import de.joshuaschnabel.wem.domain.guest.GuestId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class GuestStatus extends Aggregate<GuestStatus.GuestStatusId> {

  public static class GuestStatusId extends AggregateId<Integer> {

    public GuestStatusId(Integer value) {
      super(value);
    }
  }

  private final GuestStatusId id;

  @Setter
  private GuestId guest;

  @Setter
  private Boolean accepted;
}
