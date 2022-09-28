package org.am.library.events;

import org.am.library.events.warehouse.WarehouseEvent;

public class ImsEventFactory {

    public static ImsEvent buildWarehouseCreatedEvent(final Object source, final EventName eventName, final Object payload) {

        return new WarehouseEvent.WarehouseCreatedEventBuilder()
                .data(payload)
                .eventName(eventName)
                .build(source);
    }
}
