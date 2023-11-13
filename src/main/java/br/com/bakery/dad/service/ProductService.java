package br.com.bakery.dad.service;



import br.com.bakery.dad.dto.ProductDTO;
import br.com.bakery.dad.entities.Sale;
import br.com.bakery.dad.mapper.ModelMapperService;
import br.com.bakery.dad.repository.ProductRepository;
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
       var entity = productRepository.findById(id).orElse(null);
        return modelMapper.parseObject(entity, ProductDTO.class);

    }

    public List<ProductDTO> findAllProducts(){
        return modelMapper.parseListObjects(productRepository.findAll(), ProductDTO.class);
    }

    public ProductDTO create(ProductDTO product){
        var entity = modelMapper.parseObject(product, Product.class);
        return modelMapper.parseObject(productRepository.save(entity), ProductDTO.class);
    }

    public ProductDTO update(@org.jetbrains.annotations.NotNull ProductDTO product){
        var entity = productRepository.findById(product.getId()).orElse(null);
        assert entity != null;
        entity.setName(product.getName());
        entity.setPrice(product.getPrice());

        return modelMapper.parseObject(productRepository.save(entity), ProductDTO.class);
    }

    public void delete(Long id) {

        var entity = productRepository.findById((id)).orElse(null);
        assert entity != null;
        productRepository.delete(entity);
    }
}
