package br.com.bakery.dad.controllers;

import br.com.bakery.dad.dto.SaleDTO;
import br.com.bakery.dad.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

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
    public ResponseEntity<SaleDTO> create(@RequestBody SaleDTO sale, UriComponentsBuilder uriBuilder){
        SaleDTO saleDTO = saleService.create(sale);
        UriComponents uriComponents = uriBuilder.path("/sales/{id}").buildAndExpand(saleDTO.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponents.toUri());
        return new ResponseEntity<>(saleDTO, headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}" ,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SaleDTO> update(@PathVariable Long id, @RequestBody SaleDTO sale){
        SaleDTO updateSaleDto = saleService.update(sale);
        return ResponseEntity.ok(updateSaleDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id){
        saleService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
