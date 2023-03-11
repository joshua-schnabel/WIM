package de.joshuaschnabel.wim.domain.ddd.events;

import java.util.function.Consumer;
import reactor.core.Disposable;

public interface EventBus {
    <T> void publish(T event);

    <T extends DomainEvent> Disposable subscribe(Class<T> eventType, Consumer<T> eventHandler);

    void unsubscribe(Disposable subscription);
}
