package br.com.bakery.dad.repository;

import br.com.bakery.dad.entities.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    Page<Sale> findByDateBetween(Date startDate, Date endDate, Pageable pageable);
}
