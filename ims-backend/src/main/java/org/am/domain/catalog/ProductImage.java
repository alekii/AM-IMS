package org.am.domain.catalog;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(builderClassName = "Builder")
public class ProductImage {

    UUID sid;

    int id;

    UUID productSid;

    String imagePath;
}
