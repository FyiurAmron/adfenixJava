package se.quedro.challenge.util;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer initJackson() {
        return ( builder ) -> builder
            .defaultUseWrapper( false )
            ;
    }
}
