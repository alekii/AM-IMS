package org.am.domain.catalog;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class Category {

    UUID sid;

    String name;
}
