package com.bancolombia.proyect.domain.usecase.userUseCase;

import com.bancolombia.proyect.domain.model.user.User;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class userUseCase {

    public Mono<User> findUserById(String id){
        return null;
    }
}
