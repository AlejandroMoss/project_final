package com.bancolombia.proyect.infrastructure.entry_points.Routes;

import com.bancolombia.proyect.infrastructure.entry_points.handler.CashoutsHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRestCashout {

    @Bean
    public RouterFunction<ServerResponse> routerFunctionCashout(CashoutsHandler cashoutsHandler){
        return route(POST("/cashout/").and(accept(MediaType.APPLICATION_JSON)),cashoutsHandler::createCashoutHandler);
    }
}
