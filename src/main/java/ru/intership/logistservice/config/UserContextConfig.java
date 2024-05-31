package ru.intership.logistservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;
import ru.intership.common.UserContext;
import ru.intership.common.UserHeaderFilter;

@Configuration
public class UserContextConfig {

    @Bean
    @RequestScope
    public UserContext userContext() {
        return new UserContext();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    @Bean
    public UserHeaderFilter userHeaderFilter() {
        return new UserHeaderFilter(userContext(), objectMapper());
    }
}
