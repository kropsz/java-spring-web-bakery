package br.com.bakery.dad.service;


import br.com.bakery.dad.dto.SaleDTO;
import br.com.bakery.dad.entities.Product;
import br.com.bakery.dad.entities.Sale;
import br.com.bakery.dad.entities.SaleProduct;
import br.com.bakery.dad.entities.SaleReport;
import br.com.bakery.dad.exceptions.product.ProductNotFoundException;
import br.com.bakery.dad.exceptions.sale.SaleNotFoundExcpetion;
import br.com.bakery.dad.mapper.ModelMapperService;
import br.com.bakery.dad.repository.ProductRepository;
import br.com.bakery.dad.repository.SaleRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final ModelMapperService modelMapper;


    @Autowired
    public SaleService(SaleRepository saleRepository, ModelMapperService modelMapper, ProductRepository productRepository) {
        this.saleRepository = saleRepository;
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
    }

    public SaleDTO findById(Long id) {
        var entity = saleRepository.findById(id).orElseThrow(() -> new SaleNotFoundExcpetion(id));
        return modelMapper.parseObject(entity, SaleDTO.class);

    }

    public List<SaleDTO> findAll() {
        try {
            return modelMapper.parseListObjects(saleRepository.findAll(), SaleDTO.class);
        }catch (Exception e){
            throw new SaleNotFoundExcpetion("Error searching all sales");
        }
        }


    public SaleDTO create(SaleDTO saleDTO) {
        var saleAux = modelMapper.parseObject(saleDTO, Sale.class);
        saleAux.getSaleProducts().clear();
        if (saleDTO.getSaleProducts() != null) {
            for (SaleProduct saleProductAux : saleDTO.getSaleProducts()) {
                Product product = productRepository.findById(saleProductAux.getProduct().getId()).orElseThrow(
                        () -> new ProductNotFoundException(saleProductAux.getProduct().getId()));

                if (product != null) {
                    SaleProduct saleProduct = new SaleProduct();
                    saleProduct.setProduct(product);
                    saleProduct.setQuantity(saleProductAux.getQuantity());

                    saleProduct.setId(null);

                    saleAux.getSaleProducts().add(saleProduct);
                }
            }
        }

        Double totalPrice = calculateTotalPrice(saleAux);
        saleAux.setTotalPrice(totalPrice);

        return modelMapper.parseObject(saleRepository.save(saleAux), SaleDTO.class);
    }


    public SaleDTO update(@NotNull SaleDTO sale) {
        var entity = saleRepository.findById(sale.getId()).orElseThrow(() -> new SaleNotFoundExcpetion(sale.getId()));
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

        var entity = saleRepository.findById(id).orElseThrow(() -> new SaleNotFoundExcpetion("Couldn't find the sale to delete"));
        assert entity != null;
        saleRepository.delete(entity);
    }

    public SaleReport getSalesReportByPeriod(Date startDate, Date endDate, Pageable pageable) {
        Page<Sale> sales = saleRepository.findByDateBetween(startDate, endDate, pageable);

        SaleReport saleReport = new SaleReport();
        saleReport.setSales(modelMapper.parsePage(sales, SaleDTO.class));
        saleReport.setStartDate(startDate);
        saleReport.setEndDate(endDate);

        return saleReport;
    }

    public Double calculateTotalPrice(@NotNull Sale sale) {
        double totalPrice = 0.0;
        for (SaleProduct saleProduct : sale.getSaleProducts()) {
            Double productPrice = saleProduct.getProduct().getPrice();
            Integer quantity = saleProduct.getQuantity();
            totalPrice += productPrice * quantity;
        }

        return totalPrice;
    }
}