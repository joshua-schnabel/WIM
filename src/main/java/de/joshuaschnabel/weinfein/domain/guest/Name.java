package de.joshuaschnabel.weinfein.domain.guest;

import de.joshuaschnabel.weinfein.domain.ddd.ValueObject;
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
