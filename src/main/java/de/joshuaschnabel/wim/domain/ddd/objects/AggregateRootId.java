package de.joshuaschnabel.wim.domain.ddd.objects;

public class AggregateRootId<ValueType> extends AggregateId<ValueType> {

  public AggregateRootId(ValueType value) {
    super(value);
  }
}
