package de.joshuaschnabel.wem.domain.invitation;

import java.util.List;
import de.joshuaschnabel.wem.domain.ddd.objects.Aggregate;
import de.joshuaschnabel.wem.domain.ddd.objects.AggregateId;
import de.joshuaschnabel.wem.domain.guest.status.GuestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvitationStatus extends Aggregate<InvitationStatus.InvitationStatusId> {

  public static class InvitationStatusId extends AggregateId<Integer> {

    public InvitationStatusId(Integer value) {
      super(value);
    }
  }

  private final InvitationStatusId id;
  private final List<GuestStatus> stati;
}
