package com.bancolombia.proyect.domain.model.user.Dtos;

import java.math.BigDecimal;

public class UserDto {
    public Long id;
    public String name;
    public BigDecimal balance;


    public UserDto(String name, BigDecimal balance, Long id) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
