package br.com.bakery.dad.exceptions.product;

import br.com.bakery.dad.exceptions.BakeryException;

public class ProductCreationException extends BakeryException {
    public ProductCreationException(){
        super();
    }

    public ProductCreationException(String message){
        super(message);
    }

}
