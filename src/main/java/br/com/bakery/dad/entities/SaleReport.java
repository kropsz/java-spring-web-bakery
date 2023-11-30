package br.com.bakery.dad.entities;

import br.com.bakery.dad.dto.SaleDTO;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.Date;

@Data
public class SaleReport {
            private Page<SaleDTO> sales;
            private Date startDate;
            private Date endDate;
}