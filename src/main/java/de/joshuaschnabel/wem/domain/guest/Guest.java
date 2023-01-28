package de.joshuaschnabel.wem.domain.guest;

import java.util.Optional;
import de.joshuaschnabel.wem.domain.ddd.objects.AggregateRoot;
import de.joshuaschnabel.wem.domain.ddd.objects.IllegalObjectModificationException;
import de.joshuaschnabel.wem.domain.invitation.InvitationId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
@EqualsAndHashCode(callSuper = false)
public class Guest extends AggregateRoot<GuestId> {

  private GuestId id;

  @Builder.Default
  private Optional<Name> name = Optional.empty();

  private final GuestType type;

  @Builder.Default
  private final Optional<InvitationId> invitation = Optional.empty();

  // public void addGuestToInvitation(Invitation invitation) {
  // invitation.addGuest();
  // this.invitation = Optional.of(invitation.getId());
  // }

  @Override
  protected void setIdInternal(GuestId id) {
    this.id = id;
  }

  public void setName(Name name) {
    if (this.type == GuestType.PrimaryGuest) {
      throw new IllegalObjectModificationException("guest:name_cannot_be_changed_afterwards",
          "The name of a primary guest cannot be changed afterwards.");
    }
    this.name = Optional.of(name);
  }
}
