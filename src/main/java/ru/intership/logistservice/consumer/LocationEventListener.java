package ru.intership.logistservice.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.intership.logistservice.dto.LocationDto;
import ru.intership.logistservice.service.LocationService;

@Slf4j
@Component
@RequiredArgsConstructor
public class LocationEventListener {

    private final LocationService locationService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${spring.data.kafka.channels.location-events-channel.name}", groupId = "${spring.data.kafka.group-id}")
    public void listen(String event) {
        try {
            LocationDto locationDto = objectMapper.readValue(event, LocationDto.class);
            log.info("Location received: {}", locationDto);
            locationService.create(locationDto);
        } catch (JsonProcessingException e) {
            throw new SerializationException(e);
        }
    }
}
