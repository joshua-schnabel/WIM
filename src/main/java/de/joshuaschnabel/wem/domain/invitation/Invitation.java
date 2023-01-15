package de.joshuaschnabel.wem.domain.invitation;

import java.util.List;
import de.joshuaschnabel.wem.domain.ddd.AggregateRoot;
import de.joshuaschnabel.wem.domain.guest.GuestId;
import lombok.Getter;

@Getter
public class Invitation extends AggregateRoot<InvitationId> {

  private InvitationId id;

  private List<GuestId> guests;

  private String notificationEmail;

  private SpecialRequest specialRequest;

  @Override
  protected void setIdInternal(InvitationId id) {
    this.id = id;
  }

}
