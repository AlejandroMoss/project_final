package com.bancolombia.proyect.infrastructure.adapters.cashouts;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
@Getter
@Setter
@Table(schema = "public", name = "cashouts")
public class CashoutsData {
    @Id
    @Column("id")
    private Long id;

    @Column( "user_id")
    private Long userId;

    @Column("amount")
    private BigDecimal amount;
}
