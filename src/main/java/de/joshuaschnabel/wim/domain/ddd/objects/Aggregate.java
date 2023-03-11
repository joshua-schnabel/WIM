package de.joshuaschnabel.wim.domain.ddd.objects;

public abstract class Aggregate<Id extends AggregateId<?>> {

  public abstract Id getId();

}
