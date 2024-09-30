package com.bancolombia.proyect.domain.usecase.userUseCase;

import com.bancolombia.proyect.domain.model.user.Dtos.UserDto;
import com.bancolombia.proyect.infrastructure.adapters.user.UserAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class UserUseCase {
    private final UserAdapter user;

    public Mono<UserDto> findUserById(Long id){
        return user.getUserById(id)
                .map(userData -> new UserDto(userData.getName(), userData.getBalance(),userData.getId()));
    }

    public Mono<UserDto> saveUser(UserDto data){
        System.out.println(data);
        return user.saveUser(data);
    }

    public Mono<UserDto> updateBalance(Long id, BigDecimal amount){
        return user.updateBalance(id,amount)
                .map(userData -> new UserDto(userData.getName(), userData.getBalance(),userData.getId()));
    }
}
