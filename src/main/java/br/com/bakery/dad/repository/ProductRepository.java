package br.com.bakery.dad.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.bakery.dad.entities.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

}
