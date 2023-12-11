package br.com.bakery.dad.dto;

import br.com.bakery.dad.entities.SaleProduct;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SaleDTO {

    private Long id;
    private Double totalPrice;
    private Date date;
    private List<SaleProduct> saleProducts;
}
