package br.com.bakery.dad.exceptions.product;

import java.util.UUID;

import br.com.bakery.dad.exceptions.BakeryException;

public class ProductNotFoundException extends BakeryException {

    public ProductNotFoundException(){
        super("Product not found");
    }

    public ProductNotFoundException(String message){
        super(message);
    }
    public ProductNotFoundException(UUID productId){
        super("Product not found: " + productId);
    }
}
