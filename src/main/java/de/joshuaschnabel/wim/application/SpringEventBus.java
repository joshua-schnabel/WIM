package de.joshuaschnabel.wim.application;

import java.util.function.Consumer;

import org.springframework.stereotype.Service;

import de.joshuaschnabel.wim.domain.ddd.events.DomainEvent;
import de.joshuaschnabel.wim.domain.ddd.events.EventBus;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class SpringEventBus implements EventBus {

	private final Sinks.Many<Object> eventSink = Sinks.many().multicast().onBackpressureBuffer();

	@Override
	public <T> void publish(T event) {
		this.eventSink.tryEmitNext(event);
	}

	@Override
	public <T extends DomainEvent> Disposable subscribe(Class<T> eventType, Consumer<T> eventHandler) {
		Flux<T> eventStream = this.eventSink.asFlux().filter(eventType::isInstance).cast(eventType);
		return eventStream.subscribe(eventHandler);
	}

	@Override
	public void unsubscribe(Disposable subscription) {
		subscription.dispose();
	}
}
