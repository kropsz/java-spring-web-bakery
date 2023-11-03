package br.com.bakery.dad.repository;

import br.com.bakery.dad.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
