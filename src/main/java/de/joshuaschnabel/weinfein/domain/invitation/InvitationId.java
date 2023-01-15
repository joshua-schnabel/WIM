package de.joshuaschnabel.weinfein.domain.invitation;

import de.joshuaschnabel.weinfein.domain.ddd.AggregateId;

public class InvitationId extends AggregateId<String> {

  public InvitationId(String value) {
    super(value);
  }

}
