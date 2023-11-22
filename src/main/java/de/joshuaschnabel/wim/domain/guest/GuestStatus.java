package de.joshuaschnabel.wim.domain.guest;

import de.joshuaschnabel.wim.domain.ddd.objects.Aggregate;
import de.joshuaschnabel.wim.domain.ddd.objects.AggregateId;
import de.joshuaschnabel.wim.domain.ddd.type.BasicType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Builder
@AllArgsConstructor
public class GuestStatus extends Aggregate<GuestStatus.GuestStatusId> {

	public static class GuestStatusAccepted extends BasicType<Boolean> {

		public static GuestStatusAccepted YES = new GuestStatusAccepted(true);

		public static GuestStatusAccepted NO = new GuestStatusAccepted(false);

		public GuestStatusAccepted(Boolean value) {
			super(value);
		}
	}

	public static class GuestStatusId extends AggregateId<Integer> {

		public GuestStatusId(Integer value) {
			super(value);
		}
	}

	private final GuestStatusId id;

	private GuestId guest;

	@Builder.Default
	@Setter
	private GuestStatusAccepted accepted = new GuestStatusAccepted(false);
}
