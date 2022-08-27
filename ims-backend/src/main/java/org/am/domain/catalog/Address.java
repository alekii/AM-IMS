package org.am.domain.catalog;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(builderClassName = "Builder")
public class Address {

    String street;

    Double latitude;

    Double longitude;

    String mapUrl;

    Town town;
}
