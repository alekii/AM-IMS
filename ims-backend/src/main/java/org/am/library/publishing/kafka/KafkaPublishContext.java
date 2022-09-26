package org.am.library.publishing.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.am.library.events.ImsEvent;

@Getter
@Builder(builderClassName = "Builder")
@AllArgsConstructor
public class KafkaPublishContext {

    private ImsEvent imsEvent;

    private String topic;

    private Object payload;
}
