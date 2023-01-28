package de.joshuaschnabel.wem.domain.ddd.objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@Builder
@EqualsAndHashCode
@AllArgsConstructor
public class AggregateId<ValueType> {

  private final ValueType value;

  public ValueType get() {
    return this.value;
  }
}
