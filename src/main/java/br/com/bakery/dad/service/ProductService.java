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

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapperService modelMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ModelMapperService modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public ProductDTO findById(Long id){
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

    public ProductDTO update(@NotNull ProductDTO product){
    var entity = productRepository.findById(product.getId()).orElseThrow(() -> new ProductNotFoundException(product.getId()));
        assert entity != null;
        entity.setName(product.getName());
        entity.setPrice(product.getPrice());

        return modelMapper.parseObject(productRepository.save(entity), ProductDTO.class);
    }

    public void delete(Long id) {

        var entity = productRepository.findById((id)).orElseThrow(() -> new ProductNotFoundException(id));
        assert entity != null;
        productRepository.delete(entity);
    }
}
