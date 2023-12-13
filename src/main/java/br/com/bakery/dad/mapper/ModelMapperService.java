package br.com.bakery.dad.mapper;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModelMapperService {

    @Autowired
    private ModelMapper modelMapper;

 

    public <O, D> D parseObject(O origin, Class<D> destination) {
        return modelMapper.map(origin, destination);
    }

    public <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
        List<D> destinationObjects = new ArrayList<D>();
        for (O o : origin) {
            destinationObjects.add(modelMapper.map(o, destination));
        }
        return destinationObjects;
    }

    public <S, T> Page<T> parsePage(Page<S> sourcePage, Class<T> targetType) {
        List<T> targetList = sourcePage.getContent()
                .stream()
                .map(sourceItem -> modelMapper.map(sourceItem, targetType))
                .collect(Collectors.toList());

        return new PageImpl<>(targetList, sourcePage.getPageable(), sourcePage.getTotalElements());
    }
}