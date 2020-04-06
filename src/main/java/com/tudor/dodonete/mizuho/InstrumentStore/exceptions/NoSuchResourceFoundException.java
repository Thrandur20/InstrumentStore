package com.tudor.dodonete.mizuho.InstrumentStore.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class NoSuchResourceFoundException extends RuntimeException {
    public NoSuchResourceFoundException(String msg) {
        super(msg);
    }
}
