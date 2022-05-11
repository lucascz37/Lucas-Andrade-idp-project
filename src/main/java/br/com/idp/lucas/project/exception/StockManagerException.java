package br.com.idp.lucas.project.exception;

public class StockManagerException extends RuntimeException{

    public StockManagerException(String message){
        super(message);
    }

    public StockManagerException(String message, Throwable cause){
        super(message, cause);
    }
}
