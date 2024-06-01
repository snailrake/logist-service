package ru.intership.logistservice.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.intership.logistservice.dto.RouteEventDto;
import ru.intership.logistservice.service.RouteEventService;

@Slf4j
@Component
@RequiredArgsConstructor
public class RouteEventListener {

    private final RouteEventService routeEventService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${spring.data.kafka.channels.route-events-channel.name}", groupId = "${spring.data.kafka.group-id}")
    public void listen(String event) {
        try {
            RouteEventDto routeEvent = objectMapper.readValue(event, RouteEventDto.class);
            log.info("RouteEvent received: {}", routeEvent);
            routeEventService.create(routeEvent);
        } catch (JsonProcessingException e) {
            throw new SerializationException(e);
        }
    }
}
