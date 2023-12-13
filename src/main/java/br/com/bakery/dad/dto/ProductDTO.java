package br.com.bakery.dad.dto;

import java.util.UUID;

import lombok.Data;


@Data
public class ProductDTO {
    private UUID id;
    private String name;
    private Double price;
}
