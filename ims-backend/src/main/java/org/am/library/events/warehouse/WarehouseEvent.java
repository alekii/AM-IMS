package org.am.library.events.warehouse;

import org.am.library.events.ImsEvent;

public class WarehouseEvent extends ImsEvent<Integer> {

    private static final long serialVersionUID = 6428491835267384920L;

    public WarehouseEvent(final Object source, final ImsEventBuilder imsEventBuilder) {

        super(source, imsEventBuilder);
    }

    public static class WarehouseCreatedEventBuilder
            extends ImsEventBuilder<WarehouseEvent, WarehouseCreatedEventBuilder> {

        @Override
        protected WarehouseEvent buildEvent(Object source, ImsEventBuilder imsEventBuilder) {

            return new WarehouseEvent(source, imsEventBuilder);
        }
    }
}
