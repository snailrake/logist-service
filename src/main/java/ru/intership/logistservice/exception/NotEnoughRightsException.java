package ru.intership.logistservice.exception;

public class NotEnoughRightsException extends RuntimeException {

    public NotEnoughRightsException(String message) {
        super(message);
    }
}
