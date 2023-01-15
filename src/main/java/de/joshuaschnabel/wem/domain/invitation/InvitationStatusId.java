package de.joshuaschnabel.wem.domain.invitation;

import de.joshuaschnabel.wem.domain.ddd.AggregateId;

public class InvitationStatusId extends AggregateId<String> {

  public InvitationStatusId(String value) {
    super(value);
  }

}
