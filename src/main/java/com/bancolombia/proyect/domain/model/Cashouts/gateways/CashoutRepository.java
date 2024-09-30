package com.bancolombia.proyect.domain.model.Cashouts.gateways;

import com.bancolombia.proyect.domain.model.Cashouts.Cashout;
import reactor.core.publisher.Mono;

public interface CashoutRepository {
    Mono<Cashout> saveCashouts (Cashout cashout);
}
