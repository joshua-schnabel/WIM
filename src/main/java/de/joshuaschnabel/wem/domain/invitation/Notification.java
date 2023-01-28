package de.joshuaschnabel.wem.domain.invitation;

import java.util.Optional;
import de.joshuaschnabel.wem.domain.ddd.objects.Aggregate;
import de.joshuaschnabel.wem.domain.ddd.objects.AggregateId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Notification extends Aggregate<Notification.NotificationId> {

  public static class NotificationId extends AggregateId<Integer> {

    public NotificationId(Integer value) {
      super(value);
    }
  }

  private final NotificationId id;

  private final Boolean requested = false;

  private final Optional<String> emailAdress = Optional.empty();

}
