package com.bancolombia.proyect.infrastructure.adapters.cashouts;

import com.bancolombia.proyect.domain.model.Cashouts.Cashout;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
public class PaymentAdapter {
    private WebClient webClient;

    public PaymentAdapter(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://microservice-url").build();
    }

    public Mono<Cashout> verifyAndProcessPayment(Cashout cashout) {
        return webClient.post()
                .uri("/external/payments/cashouts")
                .bodyValue(cashout)
                .retrieve()
                .onStatus(status -> !status.is2xxSuccessful(), response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException("Payment verification failed: " + errorBody)))
                )
                .bodyToMono(Cashout.class)
                .onErrorResume(error -> {
                    // Manejar errores del microservicio
                    return Mono.error(new RuntimeException("Payment verification failed: " + error.getMessage()));
                });
    }
}
