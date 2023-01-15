package de.joshuaschnabel.wem.domain.invitation;

import java.util.List;
import de.joshuaschnabel.wem.domain.ddd.AggregateRoot;
import de.joshuaschnabel.wem.domain.guest.GuestStatus;
import lombok.Getter;

@Getter
public class InvitationStatus extends AggregateRoot<InvitationStatusId> {
  private InvitationStatusId id;

  private List<GuestStatus> stati;

  @Override
  protected void setIdInternal(InvitationStatusId id) {
    this.id = id;
  }

}
