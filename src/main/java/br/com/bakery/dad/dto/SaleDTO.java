package br.com.bakery.dad.dto;

import br.com.bakery.dad.entities.SaleProduct;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
public class SaleDTO {

    private UUID id;
    private Double totalPrice;
    private Instant date;
    private List<SaleProduct> saleProducts;
}
