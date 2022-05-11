package br.com.idp.lucas.project.exception;

import lombok.Getter;

@Getter
public class ApiRequestException extends RuntimeException{

    private Object errorCause;

    public ApiRequestException(String message, Object errorCause){
        super(message);
        this.errorCause = errorCause;
    }

    public ApiRequestException(String message, Throwable cause){
        super(message, cause);
    }
}
