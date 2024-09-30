package com.bancolombia.proyect.domain.usecase.CashoutsUseCase;

import com.bancolombia.proyect.domain.model.Cashouts.Cashout;
import com.bancolombia.proyect.infrastructure.adapters.cashouts.CashoutAdapter;
import com.bancolombia.proyect.infrastructure.adapters.cashouts.PaymentAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class CashoutsUseCase {
    private final CashoutAdapter cashoutAdapter;
    public final PaymentAdapter paymentAdapter;

    public Mono<Cashout> execute(Long userId, BigDecimal amount){
        Cashout cashout = new Cashout(userId,amount);
        return paymentAdapter.verifyAndProcessPayment(cashout)
                .flatMap(cashoutAdapter::save);
    }
}
