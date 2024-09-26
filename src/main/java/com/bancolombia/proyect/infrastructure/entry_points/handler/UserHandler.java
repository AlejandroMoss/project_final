package com.bancolombia.proyect.infrastructure.entry_points.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.bancolombia.proyect.domain.usecase.userUseCase.userUseCase;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserHandler {
    private userUseCase userUseCase;

    public Mono<ServerResponse> getUserHandler(ServerRequest id){
        return  null;
    }

}
