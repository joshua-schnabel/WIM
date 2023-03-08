package de.joshuaschnabel.wem.domain.guest;

import de.joshuaschnabel.wem.domain.ddd.objects.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor(staticName = "of")
public class GuestName extends ValueObject {

  private final String firstname;
  private final String lastname;

}
