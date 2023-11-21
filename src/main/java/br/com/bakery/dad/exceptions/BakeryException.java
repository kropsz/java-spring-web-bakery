package br.com.bakery.dad.exceptions;

public class BakeryException extends RuntimeException{

    public BakeryException(){
        super();
    }

    public BakeryException(String message){
        super(message);
    }

    public BakeryException(String message, Throwable cause){
        super(message,cause);
    }
}
