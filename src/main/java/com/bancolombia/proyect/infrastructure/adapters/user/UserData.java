package com.bancolombia.proyect.infrastructure.adapters.user;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.math.BigDecimal;


@Getter
@Setter
@Table(schema = "public", name = "users")
public class UserData {

    @Id
    @Column("id")
    private Long id;

    @Column( "name")
    private String name;

    @Column("balance")
    private BigDecimal balance;
}