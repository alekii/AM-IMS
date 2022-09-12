package org.am.library.events.warehouse;

import org.am.library.events.ImsEvent;

public class WarehouseCreatedEvent extends ImsEvent<Integer> {

    public WarehouseCreatedEvent(final Object source, final ImsEventBuilder imsEventBuilder) {

        super(source, imsEventBuilder);
    }

    public static class WarehouseCreatedEventBuilder
            extends ImsEventBuilder<WarehouseCreatedEvent, WarehouseCreatedEvent.WarehouseCreatedEventBuilder> {

        @Override
        protected WarehouseCreatedEvent buildEvent(Object source, ImsEventBuilder imsEventBuilder) {

            return new WarehouseCreatedEvent(source, imsEventBuilder);
        }
    }
}
