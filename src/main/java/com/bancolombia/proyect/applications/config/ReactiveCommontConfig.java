package com.bancolombia.proyect.applications.config;

import org.reactivecommons.utils.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReactiveCommontConfig {

    @Bean
    public ObjectMapper reactiveCommonsMapper() {
        return new ObjectMapper() {
            @Override
            public <T> T map(Object o, Class<T> aClass) {
                return null;
            }

            @Override
            public <T> T mapBuilder(Object o, Class<T> aClass) {
                return null;
            }
        };
    }
}
