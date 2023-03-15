package de.joshuaschnabel.wim.domain.guest;

import de.joshuaschnabel.wim.domain.ddd.objects.ValueObject;
import de.joshuaschnabel.wim.domain.ddd.type.BasicType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class GuestName extends ValueObject {

	public static class FirstName extends BasicType<String> {

		public FirstName(String value) {
			super(value);
		}

	}

	public static class LastName extends BasicType<String> {

		public LastName(String value) {
			super(value);
		}

	}

	public static GuestName of(FirstName firstname, LastName lastname) {
		return new GuestName(firstname, lastname);
	}

	private final FirstName firstname;

	private final LastName lastname;

}
