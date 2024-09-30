package com.bancolombia.proyect.infrastructure.adapters.cashouts;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ICashoutDataRepository extends ReactiveCrudRepository<CashoutsData,Long>, ReactiveQueryByExampleExecutor<CashoutsData> {
}
