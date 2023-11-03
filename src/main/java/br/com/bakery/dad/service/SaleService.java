package br.com.bakery.dad.service;


import br.com.bakery.dad.dto.SaleDTO;
import br.com.bakery.dad.entities.Sale;
import br.com.bakery.dad.mapper.ModelMapperService;
import br.com.bakery.dad.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final ModelMapperService modelMapper;


    @Autowired
    public SaleService(SaleRepository saleRepository, ModelMapperService modelMapper) {
        this.saleRepository = saleRepository;
        this.modelMapper = modelMapper;
    }

    public SaleDTO findById(Long id){
       return modelMapper.parseObject(saleRepository.findById(id).orElse(null), SaleDTO.class);
    }

    public List<SaleDTO> findAll(){
       return modelMapper.parseListObjects(saleRepository.findAll(), SaleDTO.class);
    }

    public SaleDTO create(SaleDTO sale){
        var entity = modelMapper.parseObject(sale, Sale.class);
        return modelMapper.parseObject(entity, SaleDTO.class);

    }

    public SaleDTO update(SaleDTO sale){
        var entity = saleRepository.findById(sale.getId()).orElse(null);
        assert entity != null;
        entity.setTotalPrice(sale.getTotalPrice());
        entity.setDate(sale.getDate());
        entity.getProducts().clear();
        if (sale.getProducts() != null){
            entity.getProducts().addAll(sale.getProducts());
        }
        return modelMapper.parseObject(saleRepository.save(entity), SaleDTO.class);
    }

    public void delete(Long id){

        var entity = saleRepository.findById(id).orElse(null);
        assert entity != null;
        saleRepository.delete(entity);
    }
}
