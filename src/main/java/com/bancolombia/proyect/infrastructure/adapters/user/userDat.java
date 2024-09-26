package com.bancolombia.proyect.infrastructure.adapters.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.relational.core.mapping.Table;

@EntityScan
@Data
@Entity
@Table(name = "User")
@NoArgsConstructor
public class userDat {

    @Column(name = "id")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "balance")
    private String balance;
}
