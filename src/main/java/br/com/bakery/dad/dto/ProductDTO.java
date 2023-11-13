package br.com.bakery.dad.dto;

import br.com.bakery.dad.entities.Sale;
import lombok.Data;

import java.util.List;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private Double price;
}
