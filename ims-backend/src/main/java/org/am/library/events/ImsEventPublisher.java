package org.am.library.events;

public interface ImsEventPublisher<X> {

    void publish(X publishContext) throws Exception;
}
