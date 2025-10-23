package org.example.newspaperjsclass.domain.service;



import jakarta.inject.Inject;
import org.example.newspaperjsclass.dao.NewspaperRepository;
import org.example.newspaperjsclass.dao.model.NewspaperEntity;
import org.example.newspaperjsclass.domain.mappers.NewspaperMapperService;
import org.example.newspaperjsclass.domain.model.NewspaperDTO;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class NewspaperService {

    private final NewspaperRepository newspaperRepository;
    private final NewspaperMapperService newspaperMapperService;


    public NewspaperService (NewspaperRepository newspaperRepository, NewspaperMapperService newspaperMapperService) {
        this.newspaperRepository = newspaperRepository;
        this.newspaperMapperService = newspaperMapperService;

    }

    public List<NewspaperDTO> getAllNewspapers() {
        List<NewspaperEntity> newspapers = newspaperRepository.getAll();
        return newspaperMapperService.mapToDTOs(newspapers);
    }
}
