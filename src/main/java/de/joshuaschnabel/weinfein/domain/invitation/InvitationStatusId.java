package de.joshuaschnabel.weinfein.domain.invitation;

import de.joshuaschnabel.weinfein.domain.ddd.AggregateId;

public class InvitationStatusId extends AggregateId<String> {

  public InvitationStatusId(String value) {
    super(value);
  }

}
