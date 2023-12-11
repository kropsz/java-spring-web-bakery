package br.com.bakery.dad.controllers;

import br.com.bakery.dad.dto.ProductDTO;
import br.com.bakery.dad.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDTO> findById(@PathVariable(value = "id") Long id){
            ProductDTO productDTO = productService.findById(id);
            if (productDTO != null)
                return ResponseEntity.ok(productDTO);
            else
                return ResponseEntity.notFound().build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductDTO>> findAll(){
        List<ProductDTO> listProduct = productService.findAllProducts();
        if (listProduct != null)
            return ResponseEntity.ok(listProduct);
        else
            return ResponseEntity.notFound().build();
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO product){
        ProductDTO productDTO = productService.create(product);
        return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
    }

    @PostMapping(value = "/create-multiple", consumes = MediaType.APPLICATION_JSON_VALUE,
                                                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductDTO>> createMultipleProducts(@RequestBody List<ProductDTO> products) {
        List<ProductDTO> createdProducts = productService.createProducts(products);
        return new ResponseEntity<>(createdProducts, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductDTO updatedProductDTO) {
            ProductDTO existingProductDTO = productService.findById(id);
            if (existingProductDTO != null) {
                existingProductDTO.setName(updatedProductDTO.getName());
                existingProductDTO.setPrice(updatedProductDTO.getPrice());
                productService.update(existingProductDTO);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{productId}/apply-discount")
        public ResponseEntity<ProductDTO> applyDiscount(@PathVariable Long productId, @RequestParam Double discount) {
            ProductDTO product = productService.applyDiscount(productId, discount);
            return ResponseEntity.ok(product);
        }


}



