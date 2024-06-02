package ru.intership.logistservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.intership.logistservice.dto.UserDto;

@FeignClient(name = "portal-service", url = "${services.portal.url.base}")
public interface PortalServiceClient {

    @GetMapping(value = "${services.portal.url.get-user-by-id}")
    UserDto getUserByUsername(@RequestParam String username);
}