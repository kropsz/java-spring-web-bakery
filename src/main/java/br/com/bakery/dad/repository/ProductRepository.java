package br.com.bakery.dad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.bakery.dad.entities.Product;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface ProductRepository extends JpaRepository<Product, Long> {

}
