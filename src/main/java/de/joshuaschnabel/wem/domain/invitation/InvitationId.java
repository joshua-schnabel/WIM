package de.joshuaschnabel.wem.domain.invitation;

import de.joshuaschnabel.wem.domain.ddd.AggregateId;

public class InvitationId extends AggregateId<String> {

  public InvitationId(String value) {
    super(value);
  }

}
