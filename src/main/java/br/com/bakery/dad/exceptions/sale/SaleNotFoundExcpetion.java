package br.com.bakery.dad.exceptions.sale;

import br.com.bakery.dad.exceptions.BakeryException;

public class SaleNotFoundExcpetion extends BakeryException {

    public SaleNotFoundExcpetion(){
        super("Sale not found");
    }

    public SaleNotFoundExcpetion(String message){
        super(message);
    }

    public SaleNotFoundExcpetion(Long saleId){
        super("Sale not found: " + saleId);
    }

    }
