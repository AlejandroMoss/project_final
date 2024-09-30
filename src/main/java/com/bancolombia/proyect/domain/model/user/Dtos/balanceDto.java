package com.bancolombia.proyect.domain.model.user.Dtos;

import java.math.BigDecimal;

public class balanceDto {
    private BigDecimal amount;

    public balanceDto(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
