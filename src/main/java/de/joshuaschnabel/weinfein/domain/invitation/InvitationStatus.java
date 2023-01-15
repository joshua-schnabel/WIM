package de.joshuaschnabel.weinfein.domain.invitation;

import java.util.List;
import de.joshuaschnabel.weinfein.domain.ddd.AggregateRoot;
import de.joshuaschnabel.weinfein.domain.guest.GuestStatus;
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
