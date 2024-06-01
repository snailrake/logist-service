package ru.intership.logistservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import ru.intership.logistservice.client.PortalServiceClient;
import ru.intership.logistservice.dto.UserDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class PortalService {

    private final PortalServiceClient portalServiceClient;

    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 5000))
    public UserDto getUserByUsername(String username) {
        return portalServiceClient.getUserByUsername(username);
    }
}
