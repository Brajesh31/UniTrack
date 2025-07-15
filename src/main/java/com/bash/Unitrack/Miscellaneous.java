package com.bash.Unitrack;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class Miscellaneous {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Unitrack API")
                        .version("1.0")
                        .description("API documentation for Unitrack")
                );
    }

    @Bean
    public WebClient webClient(WebClient.Builder builder){
        return builder.
                baseUrl("https://routes.googleapis.com")
                .build();
    }
}
