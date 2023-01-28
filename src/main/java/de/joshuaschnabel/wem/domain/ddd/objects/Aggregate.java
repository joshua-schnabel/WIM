package de.joshuaschnabel.wem.domain.ddd.objects;

public abstract class Aggregate<Id extends AggregateId<?>> {

  public abstract Id getId();

}
