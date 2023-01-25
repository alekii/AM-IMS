package org.am.rest.services.responses;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProductImageResponse {

    int id;

    String imagePath;
}
