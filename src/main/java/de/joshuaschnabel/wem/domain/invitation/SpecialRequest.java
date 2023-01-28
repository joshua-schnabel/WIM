package de.joshuaschnabel.wem.domain.invitation;

import de.joshuaschnabel.wem.domain.ddd.objects.Aggregate;
import de.joshuaschnabel.wem.domain.ddd.objects.AggregateId;
import de.joshuaschnabel.wem.domain.invitation.SpecialRequest.SpecialRequestId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SpecialRequest extends Aggregate<SpecialRequestId> {

  public static class SpecialRequestId extends AggregateId<Integer> {

    public SpecialRequestId(Integer value) {
      super(value);
    }
  }

  private final SpecialRequestId id;

  private final String request;

  private final Boolean accepted;

  private final String answer;

}
