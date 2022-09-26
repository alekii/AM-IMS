package org.am.library.events.warehouse;

import org.am.library.events.ImsEvent;

public class WarehouseCreatedEvent extends ImsEvent<Integer> {

    private static final long serialVersionUID = 6428491835267384920L;

    public WarehouseCreatedEvent(final Object source, final ImsEventBuilder imsEventBuilder) {

        super(source, imsEventBuilder);
    }

    public static class WarehouseCreatedEventBuilder
            extends ImsEventBuilder<WarehouseCreatedEvent, WarehouseCreatedEventBuilder> {

        @Override
        protected WarehouseCreatedEvent buildEvent(Object source, ImsEventBuilder imsEventBuilder) {

            return new WarehouseCreatedEvent(source, imsEventBuilder);
        }
    }
}
