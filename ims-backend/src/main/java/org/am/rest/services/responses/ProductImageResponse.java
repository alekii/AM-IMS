package org.am.rest.services.responses;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class ProductImageResponse {

    int id;

    UUID sid;

    String imagePath;
}
