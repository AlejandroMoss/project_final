package com.bancolombia.proyect.infrastructure.adapters.user;

import com.bancolombia.proyect.domain.model.user.User;
import com.bancolombia.proyect.domain.model.user.gateways.userRepository;
import com.bancolombia.proyect.infrastructure.adapters.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class userAdapter extends ReactiveAdapterOperations<User, userDat ,Long, IUserRepository> implements userRepository {

    public userAdapter(IUserRepository repository,ObjectMapper mapper) {
        super(repository, mapper, d->mapper.map(d,User.class));
    }
    @Override
    public Mono<User> getUserById(String id) {
        return null;
    }
}
