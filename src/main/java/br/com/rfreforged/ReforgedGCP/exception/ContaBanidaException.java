package br.com.rfreforged.ReforgedGCP.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ContaBanidaException extends Exception {
    public ContaBanidaException(String message) {
        super(message);
    }

    public ContaBanidaException(String message, Throwable cause) {
        super(message, cause);
    }
}
