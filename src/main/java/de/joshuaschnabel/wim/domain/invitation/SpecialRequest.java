package de.joshuaschnabel.wim.domain.invitation;

import de.joshuaschnabel.wim.domain.ddd.objects.Aggregate;
import de.joshuaschnabel.wim.domain.ddd.objects.AggregateId;
import de.joshuaschnabel.wim.domain.ddd.type.BasicType;
import de.joshuaschnabel.wim.domain.invitation.SpecialRequest.SpecialRequestId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Builder
@EqualsAndHashCode(callSuper = false)
public class SpecialRequest extends Aggregate<SpecialRequestId> {

	public static class Request extends BasicType<String> {
		public Request(String value) {
			super(value);
		}
	}

	public static class RequestAnser extends BasicType<String> {

		public RequestAnser(String value) {
			super(value);
		}
	}

	public static class RequestStatus extends BasicType<Boolean> {
		public static RequestStatus YES = new RequestStatus(true);

		public static RequestStatus NO = new RequestStatus(false);

		public RequestStatus(Boolean value) {
			super(value);
		}
	}

	public static class SpecialRequestId extends AggregateId<Integer> {

		public SpecialRequestId(Integer value) {
			super(value);
		}
	}

	private final SpecialRequestId id;

	private final Request request;

	@Setter
	@Builder.Default
	private RequestStatus accepted = RequestStatus.NO;

	@Setter
	private RequestAnser answer;

}
