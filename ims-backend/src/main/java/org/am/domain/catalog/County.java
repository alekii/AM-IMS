package org.am.domain.catalog;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class County {

    UUID sid;

    String name;
}
