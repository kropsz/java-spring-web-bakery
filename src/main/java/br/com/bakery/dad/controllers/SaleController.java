package br.com.bakery.dad.controllers;

import br.com.bakery.dad.dto.SaleDTO;
import br.com.bakery.dad.repository.SaleRepository;
import br.com.bakery.dad.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public SaleDTO findById(@PathVariable(value = "id")  Long id){
        return saleService.findById(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SaleDTO> findAll(){
        return saleService.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SaleDTO create(@RequestBody SaleDTO sale){
        return saleService.create(sale);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SaleDTO update(@RequestBody SaleDTO sale){
        return saleService.update(sale);
    }

    @DeleteMapping(value = "/id")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id){
        saleService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
