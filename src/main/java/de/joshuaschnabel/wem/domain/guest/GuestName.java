package de.joshuaschnabel.wem.domain.guest;

import de.joshuaschnabel.wem.domain.ddd.objects.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class GuestName extends ValueObject {

    public static GuestName of(String firstname, String lastname) {
        return new GuestName(firstname, lastname);
    }

    private final String firstname;

    private final String lastname;

}
