package com.bancolombia.proyect.domain.model.Cashouts;

import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Cashout {
    private Long id;
    private Long userId;
    private BigDecimal amount;


    public Cashout(Long userId, BigDecimal amount) {
    }
}
