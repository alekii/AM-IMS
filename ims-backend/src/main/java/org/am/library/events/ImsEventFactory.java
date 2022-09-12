package org.am.library.events;

import org.am.library.events.warehouse.WarehouseCreatedEvent;

public class ImsEventFactory {

    private static ImsEvent buildWarehouseCreatedEvent(final Object source, final EventName eventName, final Object payload) {

        return new WarehouseCreatedEvent.WarehouseCreatedEventBuilder()
                .data(payload)
                .eventName(eventName)
                .build(source);
    }
}
