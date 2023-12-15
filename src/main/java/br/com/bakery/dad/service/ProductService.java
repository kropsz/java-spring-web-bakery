package br.com.bakery.dad.service;



import br.com.bakery.dad.dto.ProductDTO;
import br.com.bakery.dad.exceptions.product.ProductCreationException;
import br.com.bakery.dad.exceptions.product.ProductNotFoundException;
import br.com.bakery.dad.mapper.ModelMapperService;
import br.com.bakery.dad.repository.ProductRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.bakery.dad.entities.Product;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapperService modelMapper;

    
 

    public ProductDTO findById(UUID id){
       var entity = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        return modelMapper.parseObject(entity, ProductDTO.class);

    }

    public List<ProductDTO> findAllProducts(){
        try{
        return modelMapper.parseListObjects(productRepository.findAll(), ProductDTO.class);
        }catch (Exception e){
            throw new ProductNotFoundException("Error searching all products");
        }
    }

    public ProductDTO create(ProductDTO product){
        try {
            var entity = modelMapper.parseObject(product, Product.class);
            return modelMapper.parseObject(productRepository.save(entity), ProductDTO.class);
        }catch (Exception e){
            throw new ProductCreationException("Error creating product");
        }

    }

    public List<ProductDTO> createProducts(List<ProductDTO> productDTOs) {
        List<Product> products = modelMapper.parseListObjects(productDTOs, Product.class);
        List<Product> createdProducts = productRepository.saveAll(products);

        return modelMapper.parseListObjects(createdProducts, ProductDTO.class);
    }

    public ProductDTO update(@NotNull ProductDTO product){
    var entity = productRepository.findById(product.getId()).orElseThrow(() ->
            new ProductNotFoundException(product.getId()));
        assert entity != null;
        entity.setName(product.getName());
        entity.setPrice(product.getPrice());

        return modelMapper.parseObject(productRepository.save(entity), ProductDTO.class);
    }

    public boolean delete(UUID id) {

        var entity = productRepository.findById((id)).orElseThrow(() -> new ProductNotFoundException(id));
        if (entity != null) {
            productRepository.delete(entity);
            return true;
        }
        return false;
    }

    public ProductDTO applyDiscount(UUID id, Double discountPercentage){
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        if (product != null) {
            Double discountPrice = calculateDiscountedPrice(product.getPrice(), discountPercentage);
            product.setPrice(discountPrice);
        }
        return modelMapper.parseObject(product, ProductDTO.class);
    }

    private Double calculateDiscountedPrice(Double price, Double discountPercentage) {
        return price - (price * discountPercentage / 100);
    }
}
