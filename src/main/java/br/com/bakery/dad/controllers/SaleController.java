package br.com.bakery.dad.controllers;

import br.com.bakery.dad.dto.SaleDTO;
import br.com.bakery.dad.entities.SaleReport;
import br.com.bakery.dad.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/sales")
public class SaleController {

    private final SaleService saleService;

    @Autowired
    public SaleController(SaleService saleService){
        this.saleService = saleService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SaleDTO> findById(@PathVariable(value = "id")  Long id){
        SaleDTO saleDTO = saleService.findById(id);
        if (saleDTO != null)
            return ResponseEntity.ok(saleDTO);
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SaleDTO>> findAll(){
        List<SaleDTO> listSale = saleService.findAll();
        if (listSale != null)
            return ResponseEntity.ok(listSale);
        else
            return ResponseEntity.notFound().build();

    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SaleDTO> create(@RequestBody SaleDTO sale){
        SaleDTO saleDTO = saleService.create(sale);
        return new ResponseEntity<>(saleDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}" ,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SaleDTO> update(@PathVariable Long id, @RequestBody SaleDTO sale){
        SaleDTO existingSaleDTO = saleService.findById(id);
        if (existingSaleDTO != null) {
            existingSaleDTO.setTotalPrice(sale.getTotalPrice());
            existingSaleDTO.setDate(sale.getDate());
            existingSaleDTO.setSaleProducts(sale.getSaleProducts());
            saleService.update(existingSaleDTO);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id){
        saleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/report")
    public ResponseEntity<SaleReport> getSalesReportByPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            Pageable pageable) {
        SaleReport saleReport = saleService.getSalesReportByPeriod(startDate, endDate, pageable);
        return ResponseEntity.ok(saleReport);
    }

}
