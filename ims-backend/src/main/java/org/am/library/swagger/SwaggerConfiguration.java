package org.am.library.swagger;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    private static final String TITLE = "IMS API";

    private static final String DESCRIPTION = "This is the documentation of Ims Api";

    private static final String VERSION = "v1.0";

    @Bean
    OpenAPI imsOpenpApi() {

        return new OpenAPI()
                .info(new Info()
                              .title(TITLE)
                              .description(DESCRIPTION)
                              .version(VERSION))
                .externalDocs(new ExternalDocumentation()
                                      .description("Check this doc on wikipedia")
                                      .url("https://en.wikipedia.org/wiki/Truce_of_Calais"));
    }
}

