package de.joshuaschnabel.wem.domain.invitation;

import java.util.List;
import de.joshuaschnabel.wem.domain.ddd.objects.AggregateRoot;
import de.joshuaschnabel.wem.domain.guest.status.GuestStatus;
import lombok.Getter;

@Getter
public class Invitation extends AggregateRoot<InvitationId> {

  private InvitationId id;

  private List<GuestStatus> guestStati;

  private InvitationStatus status;

  private Notification notification;

  private SpecialRequest specialRequest;

  @Override
  protected void setIdInternal(InvitationId id) {
    this.id = id;
  }

}
