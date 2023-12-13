package br.com.bakery.dad.entities;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="0.00")
    private Double price;
}
