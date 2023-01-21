package org.am.rest.services.responses;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
@RequiredArgsConstructor
public class ProductImageResponse {

    int id;

    String imagePath;
}
