package de.joshuaschnabel.wim.domain.ddd.objects;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public abstract class AggregateRoot<Id extends AggregateRootId<?>> {

  public abstract Id getId();

  protected void setId(Id id) {
    if (this.getId() != null) {
      throw new RuntimeException("Id is already set");
    }
    this.setIdInternal(id);
  }

  protected abstract void setIdInternal(Id id);
}
