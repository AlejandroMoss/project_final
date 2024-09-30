package com.bancolombia.proyect.infrastructure.adapters.user;

import com.bancolombia.proyect.domain.model.user.User;
import com.bancolombia.proyect.domain.model.user.Dtos.UserDto;
import com.bancolombia.proyect.domain.model.user.gateways.UserRepository;
import com.bancolombia.proyect.infrastructure.adapters.helper.ReactiveAdapterOperations;


import org.reactivecommons.utils.ObjectMapper;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Repository
public class UserAdapter extends ReactiveAdapterOperations<User, UserData,Long, IUserDataRepository> implements UserRepository {


    public UserAdapter(IUserDataRepository repository, ObjectMapper mapper) {
        super(repository,mapper, d -> mapper.map(d,User.class));
    }
    @Override
    public Mono<User> getUserById(Long id) {
        return repository.findById(id)
                .map(this::toUser)
                .doOnNext(record -> System.out.println("USER FOUND" + record))
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")));
    }

    @Override
    public Mono<UserDto> saveUser(UserDto data) {
        UserData userData = new UserData();
        userData.setName(data.getName());
        userData.setBalance(data.getBalance());
        return repository.save(userData)
                .map(saveUserData -> new UserDto(saveUserData.getName(),saveUserData.getBalance(),saveUserData.getId()));
    }

    public Mono<UserData> updateBalance(Long id,BigDecimal amount){
       return repository.findById(id)
               .flatMap(userData -> {

                   BigDecimal newBalance = userData.getBalance().add(amount);
                   System.out.println("balance: " + newBalance);
                   userData.setBalance(newBalance);
                   return repository.save(userData);
               })
               .switchIfEmpty(Mono.error(new RuntimeException("User not found")));
    }

    private User toUser(UserData data){
        return User.builder()
                .id(data.getId())
                .name(data.getName())
                .balance(data.getBalance())
                .build();
    }
}
