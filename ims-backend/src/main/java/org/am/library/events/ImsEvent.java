package org.am.library.events;

import lombok.Getter;
import org.am.library.events.warehouse.WarehouseCreatedEvent;
import org.springframework.context.ApplicationEvent;

import java.time.Instant;

@Getter
public class ImsEvent<X> extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    EventName eventName;

    X data;

    Instant publishDate;

    public ImsEvent(final Object source, ImsEventBuilder imsEventBuilder) {

        super(source);
        this.eventName = imsEventBuilder.eventName;
        this.data = (X) imsEventBuilder.data;
        this.publishDate = Instant.now();
    }

    public static abstract class ImsEventBuilder<S, T> {

        EventName eventName;

        Object data;

        Instant publishDate;

        public T eventName(final EventName eventName) {

            this.eventName = eventName;
            return (T) this;
        }

        public T data(final Object data) {

            this.data = data;
            return (T) this;
        }

        public T publishDate(final Instant publishDate) {

            this.publishDate = publishDate;
            return (T) this;
        }

        public WarehouseCreatedEvent build(final Object source) {

            return buildEvent(source, this);
        }

        protected abstract WarehouseCreatedEvent buildEvent(Object source, ImsEventBuilder<S, T> stImsEventBuilder);
    }
}
