package com.bancolombia.proyect.infrastructure.entry_points.handler;

import com.bancolombia.proyect.domain.model.user.Dtos.UserDto;
import com.bancolombia.proyect.domain.model.user.Dtos.balanceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import com.bancolombia.proyect.domain.usecase.userUseCase.UserUseCase;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class UserHandler {
    private final UserUseCase useCase;

    public Mono<ServerResponse> getUserHandler(ServerRequest request){

        Long id = Long.parseLong(request.pathVariable("id"));
        return useCase.findUserById(id)
                .flatMap(userDto -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(userDto))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse>saveUserHandler(ServerRequest request){

        return request.bodyToMono(UserDto.class)
                .flatMap(useCase::saveUser)
                .flatMap(user -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(user))
                .onErrorResume(e -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .bodyValue("FAILED TO SAVE USER: " + e.getMessage()));

    }

    public Mono<ServerResponse> updateBalanceHandler(ServerRequest request) {
        Long id = Long.parseLong(request.pathVariable("id"));

        return request.bodyToMono(balanceDto.class)
                .flatMap(dto -> {
                    BigDecimal amount = dto.getAmount();
                    if (amount == null) {
                        return Mono.error(new RuntimeException("Amount cannot be null"));
                    }
                    System.out.println("Amount received: " + amount);
                    return useCase.updateBalance(id, amount);
                })
                .flatMap(updatedUserDto ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(updatedUserDto))
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(e ->
                        ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .bodyValue("Error updating balance: " + e.getMessage()));
    }

}