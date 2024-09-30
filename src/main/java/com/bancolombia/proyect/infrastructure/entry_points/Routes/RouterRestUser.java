package com.bancolombia.proyect.infrastructure.entry_points.Routes;

import com.bancolombia.proyect.infrastructure.entry_points.handler.CashoutsHandler;
import com.bancolombia.proyect.infrastructure.entry_points.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class RouterRestUser {
    @Bean
    public RouterFunction<ServerResponse> routerFunction(UserHandler userHandler){
        return route(GET("/users/{id}"), userHandler::getUserHandler)
                .andRoute(POST("/users"), userHandler::saveUserHandler)
                .andRoute(PUT("/users/{id}/balance"),userHandler::updateBalanceHandler);
    }


}
