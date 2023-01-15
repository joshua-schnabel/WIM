package de.joshuaschnabel.weinfein.domain.guest;

import de.joshuaschnabel.weinfein.domain.ddd.AggregateRoot;
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
