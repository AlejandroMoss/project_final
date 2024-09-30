package com.bancolombia.proyect.domain.model.user.gateways;

import com.bancolombia.proyect.domain.model.user.User;
import com.bancolombia.proyect.domain.model.user.Dtos.UserDto;
import com.bancolombia.proyect.infrastructure.adapters.user.UserData;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface UserRepository {
    Mono<User> getUserById(Long id);
    Mono<UserDto> saveUser(UserDto data);
    Mono<UserData> updateBalance(Long id, BigDecimal amount);
}
