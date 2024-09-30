package com.bancolombia.proyect.domain.model.Cashouts.Dtos;

import java.math.BigDecimal;

public class CashoutResquestDto {
    private Long userId;
    private BigDecimal amount;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
