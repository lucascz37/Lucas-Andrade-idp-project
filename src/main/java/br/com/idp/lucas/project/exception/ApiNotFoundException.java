package br.com.idp.lucas.project.exception;

public class ApiNotFoundException extends ApiRequestException{

    public ApiNotFoundException(String message, Object errorCause) {
        super(message, errorCause);
    }
}
