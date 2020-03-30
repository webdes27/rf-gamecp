package br.com.rfreforged.ReforgedGCP.exception;

public class NomePersonagemException extends Exception {
    public NomePersonagemException(String message) {
        super(message);
    }
    public NomePersonagemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
