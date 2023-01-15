package de.joshuaschnabel.weinfein.domain.ddd;

public abstract class Aggregate<Id extends AggregateId<?>> {

  public abstract Id getId();

  protected void setId(Id id) {
    if (this.getId() != null) {
      throw new RuntimeException("Id is already set");
    }
    this.setIdInternal(id);
  }

  protected abstract void setIdInternal(Id id);
}
