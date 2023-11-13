package br.com.bakery.dad.service;


import br.com.bakery.dad.dto.SaleDTO;
import br.com.bakery.dad.entities.Product;
import br.com.bakery.dad.entities.Sale;
import br.com.bakery.dad.entities.SaleProduct;
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

    public SaleDTO findById(Long id) {
        return modelMapper.parseObject(saleRepository.findById(id).orElse(null), SaleDTO.class);
    }

    public List<SaleDTO> findAll() {
        return modelMapper.parseListObjects(saleRepository.findAll(), SaleDTO.class);
    }

    public SaleDTO create(SaleDTO sale) {
        Sale saleAux = modelMapper.parseObject(sale, Sale.class);
        Double totalPrice = calculateTotalPrice(saleAux);
        saleAux.setTotalPrice(totalPrice);
        return modelMapper.parseObject(saleRepository.save(saleAux), SaleDTO.class);
    }

    public SaleDTO update(SaleDTO sale) {
        var entity = saleRepository.findById(sale.getId()).orElse(null);
        assert entity != null;
        entity.setTotalPrice(sale.getTotalPrice());
        entity.setDate(sale.getDate());
        entity.getSaleProducts().clear();
        if (sale.getSaleProducts() != null) {
            entity.getSaleProducts().addAll(modelMapper.parseListObjects(sale.getSaleProducts(), SaleProduct.class));
        }
        return modelMapper.parseObject(saleRepository.save(entity), SaleDTO.class);
    }

    public void delete(Long id) {

        var entity = saleRepository.findById(id).orElse(null);
        assert entity != null;
        saleRepository.delete(entity);
    }

    public Double calculateTotalPrice(Sale sale) {
        double totalPrice = 0.0;
        for (SaleProduct saleProduct : sale.getSaleProducts()) {
            Double productPrice = saleProduct.getProduct().getPrice();
            Integer quantity = saleProduct.getQuantity();
            totalPrice += productPrice * quantity;
        }

        return totalPrice;
    }
}