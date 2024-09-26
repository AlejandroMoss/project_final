package com.bancolombia.proyect.infrastructure.adapters.user;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IUserRepository extends ReactiveCrudRepository<userDat, Long>, ReactiveQueryByExampleExecutor<userDat> {
}
