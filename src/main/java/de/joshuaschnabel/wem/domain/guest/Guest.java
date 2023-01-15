package de.joshuaschnabel.wem.domain.guest;

import de.joshuaschnabel.wem.domain.ddd.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor(staticName = "of")
public class Guest extends AggregateRoot<GuestId> {

  private GuestId id;

  @Setter
  private Name name;

  @Override
  protected void setIdInternal(GuestId id) {
    this.id = id;
  }
}
