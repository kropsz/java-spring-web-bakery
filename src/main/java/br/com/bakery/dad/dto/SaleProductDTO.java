package br.com.bakery.dad.dto;

import br.com.bakery.dad.entities.Product;
import br.com.bakery.dad.entities.Sale;
import lombok.Data;

@Data
public class SaleProductDTO {
    private Long id;
    private Sale sale;
    private Product product;
    private Integer quantity;
}
