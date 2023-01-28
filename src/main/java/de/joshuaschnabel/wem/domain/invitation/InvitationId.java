package de.joshuaschnabel.wem.domain.invitation;

import de.joshuaschnabel.wem.domain.ddd.objects.AggregateRootId;

public class InvitationId extends AggregateRootId<String> {

  public InvitationId(String value) {
    super(value);
  }

}
