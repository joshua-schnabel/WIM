package de.joshuaschnabel.wem.domain.guest;

import de.joshuaschnabel.wem.domain.ddd.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor(staticName = "of")
public class Name extends ValueObject {

  private final String firstname;
  private final String lastname;

}
