package br.com.bakery.dad.exceptions.sale;

import br.com.bakery.dad.exceptions.BakeryException;

public class SaleCreationException extends BakeryException {

    public SaleCreationException(){
        super();
    }

    public SaleCreationException(String message){
        super(message);
    }

}
