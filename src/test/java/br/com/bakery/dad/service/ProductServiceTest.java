package br.com.bakery.dad.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.bakery.dad.dto.ProductDTO;
import br.com.bakery.dad.entities.Product;
import br.com.bakery.dad.mapper.ModelMapperService;
import br.com.bakery.dad.repository.ProductRepository;


@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para o serviço de produtos")
public class ProductServiceTest {


    @Mock
    ProductRepository productRepository;

    @Mock
    ModelMapperService modelMapper;

    @InjectMocks
    ProductService productService;


 
 @Test
 @DisplayName("Verificar se o findById retorna o produto correto")
    void testFindById() {

        UUID productId = UUID.randomUUID();
        Product mockProduct = new Product();
        mockProduct.setId(productId);
        mockProduct.setName("Test Product");
        mockProduct.setPrice(10.0);

        ProductDTO expectedProductDTO = new ProductDTO();
        expectedProductDTO.setId(mockProduct.getId());
        expectedProductDTO.setName(mockProduct.getName());
        expectedProductDTO.setPrice(mockProduct.getPrice());

        when(productRepository.findById(productId)).thenReturn(Optional.<Product>of(mockProduct));
        when(modelMapper.parseObject(mockProduct, ProductDTO.class)).thenReturn(expectedProductDTO);

        ProductDTO result = productService.findById(productId);

        assertEquals(expectedProductDTO, result);
    }

    @Test
    @DisplayName("Verificar se o findAll retorna todos os produtos corretamente")
    void testFindAll(){
         List<Product> mockProducts = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setName("Test Product " + i);
        product.setPrice(10.0 + i);
        mockProducts.add(product);
    }

    List<ProductDTO> expectedProductDTOs = mockProducts.stream()
        .map(product -> {
            ProductDTO dto = new ProductDTO();
            dto.setId(product.getId());
            dto.setName(product.getName());
            dto.setPrice(product.getPrice());
            return dto;
        })
        .collect(Collectors.toList());


        when(productRepository.findAll()).thenReturn(mockProducts);
        when(modelMapper.parseListObjects(mockProducts, ProductDTO.class)).thenReturn(expectedProductDTOs);
        List<ProductDTO> result = productService.findAllProducts();

        assertEquals(expectedProductDTOs, result);
    }

    
    @Test
    @DisplayName("Verificar se o create retorna o produto corretamente")
    void testCreate() {
        UUID productId = UUID.randomUUID();
        ProductDTO mockProductDTO = new ProductDTO();
        mockProductDTO.setId(productId);
        mockProductDTO.setName("Test Product");
        mockProductDTO.setPrice(10.0);
    
        Product mockProduct = new Product();
        mockProduct.setId(mockProductDTO.getId());
        mockProduct.setName(mockProductDTO.getName());
        mockProduct.setPrice(mockProductDTO.getPrice());
    
        when(modelMapper.parseObject(mockProductDTO, Product.class)).thenReturn(mockProduct);
        when(productRepository.save(mockProduct)).thenReturn(mockProduct);
        when(modelMapper.parseObject(mockProduct, ProductDTO.class)).thenReturn(mockProductDTO);
    
        ProductDTO result = productService.create(mockProductDTO);
    
        assertEquals(mockProductDTO, result);
    }

    @Test
    void testCreateProducts() {

    }

    @Test
    void testUpdate() {

    }

    @Test
    void testDelete() {

    }

       @Test
    void testApplyDiscount() {

    }
}
