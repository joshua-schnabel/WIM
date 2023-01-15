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
@AllArgsConstructor
public class GuestStatus extends AggregateRoot<GuestStatusId> {

  private GuestStatusId id;

  @Setter
  private GuestId guest;

  @Setter
  private Boolean accepted;

  @Override
  protected void setIdInternal(GuestStatusId id) {
    this.id = id;
  }
}
