package com.bancolombia.proyect.infrastructure.entry_points.handler;

import com.bancolombia.proyect.domain.model.Cashouts.Dtos.CashoutResquestDto;
import com.bancolombia.proyect.domain.usecase.CashoutsUseCase.CashoutsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CashoutsHandler {
    private CashoutsUseCase cashoutsUseCase;

    public Mono<ServerResponse> createCashoutHandler (ServerRequest request){
        return request.bodyToMono(CashoutResquestDto.class)
                .flatMap(cashoutResquest ->{
                    return cashoutsUseCase.execute(cashoutResquest.getUserId(),cashoutResquest.getAmount())
                            .flatMap(cashout -> ServerResponse.ok()
                                    .bodyValue(cashout))
                            .switchIfEmpty(ServerResponse.badRequest().build());
                } )
                .onErrorResume(error->{
                    return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .bodyValue("Error processing cashout: " + error.getMessage());
                });
    }
}
