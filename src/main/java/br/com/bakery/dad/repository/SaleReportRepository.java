package br.com.bakery.dad.repository;

import br.com.bakery.dad.entities.SaleReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleReportRepository extends JpaRepository<SaleReport, Long> {
}
