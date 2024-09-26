package com.bancolombia.proyect.domain.model.user.gateways;

import com.bancolombia.proyect.domain.model.user.User;
import reactor.core.publisher.Mono;

public interface userRepository {
    Mono<User> getUserById(String id);
}
