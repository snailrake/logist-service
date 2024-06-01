package ru.intership.logistservice.handler;

import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.intership.logistservice.exception.NotEnoughRightsException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(NotEnoughRightsException.class)
    public ErrorResponse handleNotEnoughRightsException(NotEnoughRightsException e) {
        log.error("NotEnoughRightsException", e);
        return new ErrorResponse(e.getMessage(), HttpStatus.FORBIDDEN.value());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponse handleEntityNotFoundException(EntityNotFoundException e) {
        log.error("EntityNotFoundException", e);
        return new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(FeignException.class)
    public ErrorResponse handleFeignException(FeignException e) {
        log.error("FeignException", e);
        return new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
