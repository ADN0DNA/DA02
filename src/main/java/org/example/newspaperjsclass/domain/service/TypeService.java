package org.example.newspaperjsclass.domain.service;

import jakarta.inject.Inject;
import org.example.newspaperjsclass.dao.TypeRepository;
import org.example.newspaperjsclass.domain.mappers.TypeMapperService;
import org.example.newspaperjsclass.domain.model.TypeDTO;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class TypeService {

    private final TypeRepository typeRepository;
    private final TypeMapperService typeMapperService;

    public TypeService(TypeRepository typeRepository, TypeMapperService typeMapperService) {
        this.typeRepository = typeRepository;
        this.typeMapperService = typeMapperService;
    }

    public List<TypeDTO> getAllTypes() {
        List types = typeRepository.getAll();
        return typeMapperService.mapToDTOs(types);
    }
}
