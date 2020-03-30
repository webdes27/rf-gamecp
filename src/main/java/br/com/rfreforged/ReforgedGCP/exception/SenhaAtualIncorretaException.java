package br.com.rfreforged.ReforgedGCP.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class SenhaAtualIncorretaException extends Exception {
    public SenhaAtualIncorretaException(String message) {
        super(message);
    }

    public SenhaAtualIncorretaException(String message, Throwable cause) {
        super(message, cause);
    }
}
