package com.bash.Unitrack.Utilities;

import com.bash.Unitrack.Data.DTO.ResponseDTO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Component
public class RouteRequest {

    private final WebClient webClient;

    public RouteRequest(WebClient webClient){
        this.webClient = webClient;
    }

    public Mono<String > computeRoute(RequestClass request, String apikey){
        return webClient
                .post()
                .uri("/directions/v2:computeRoutes")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-Goog-Api-Key", apikey)
                .header("X-Goog-FieldMask", "routes.duration,routes.distanceMeters")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    return Mono.error(new RuntimeException("API request failed: " + ex.getResponseBodyAsString()));
                });
    }
}
