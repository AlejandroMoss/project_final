package com.bancolombia.proyect.applications.config;


import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.postgresql.client.SSLMode;
import lombok.extern.slf4j.Slf4j;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@Slf4j
public class PostgreSQLConnectionPool {
    public static final int INITIAL_SIZE = 12;
    public static final int MAXIMUM_POOL_SIZE = 15;
    public static final int MAXIMUM_IDLE_TIME = 30;

    @Bean
    public ConnectionPool buildConnectionConfiguration(){
        PostgresqlConnectionConfiguration dbConfiguration = PostgresqlConnectionConfiguration.builder()
                .host("localhost")
                .port(5432)
                .database("postgres")
                .schema("public")
                .username("usuario")
                .password("")
                .sslMode(SSLMode.DISABLE)
                .build();
        ConnectionPoolConfiguration   poolConfiguration = ConnectionPoolConfiguration.builder()
                .connectionFactory(new PostgresqlConnectionFactory(dbConfiguration))
                .name("api-postgres-conection")
                .initialSize(INITIAL_SIZE)
                .maxSize(MAXIMUM_POOL_SIZE)
                .maxIdleTime(Duration.ofMinutes(MAXIMUM_IDLE_TIME))
                .validationQuery("SELECT 1")
                .build();

        ConnectionPool pool = new ConnectionPool(poolConfiguration);
        pool.create()
                .flatMapMany(connection -> connection
                        .createStatement("SELECT 1")
                        .execute())
                .flatMap(result -> result
                        .map((row, rowMetadata) -> row.get(0)))
                .doOnNext(value -> System.out.println("Connection to DB successful, result: " + value))
                .doOnError(error -> System.err.println("Error connecting to DB: " + error.getMessage()))
                .subscribe();
        return pool;
    }

}
