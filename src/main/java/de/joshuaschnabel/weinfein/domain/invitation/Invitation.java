package de.joshuaschnabel.weinfein.domain.invitation;

import java.util.List;
import de.joshuaschnabel.weinfein.domain.ddd.AggregateRoot;
import de.joshuaschnabel.weinfein.domain.guest.GuestId;
import lombok.Getter;

@Getter
public class Invitation extends AggregateRoot<InvitationId> {

  private InvitationId id;

  private List<GuestId> guests;
  
  private Notification notification;
  
  private SpecialRequest specialRequest;

  @Override
  protected void setIdInternal(InvitationId id) {
    this.id = id;
  }

}
