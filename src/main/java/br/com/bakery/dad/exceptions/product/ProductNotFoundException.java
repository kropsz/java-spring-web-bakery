package br.com.bakery.dad.exceptions.product;

import br.com.bakery.dad.exceptions.BakeryException;

public class ProductNotFoundException extends BakeryException {

    public ProductNotFoundException(){
        super("Product not found");
    }

    public ProductNotFoundException(String message){
        super(message);
    }
    public ProductNotFoundException(Long productId){
        super("Product not found: " + productId);
    }
}
