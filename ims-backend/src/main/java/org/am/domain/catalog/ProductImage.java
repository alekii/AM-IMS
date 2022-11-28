package org.am.domain.catalog;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class ProductImage {

    int id;

    UUID sid;

    UUID productSid;

    String imagePath;
}
