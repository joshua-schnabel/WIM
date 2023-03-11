package de.joshuaschnabel.wim.domain.ddd.events;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class EventBusProvider implements ApplicationContextAware {

    private static EventBus eventBus;

    public static EventBus getEventBus() {
        return eventBus;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        eventBus = applicationContext.getBean(EventBus.class);
    }
}
