package org.example.newspaperjsclass.domain.service;

import jakarta.inject.Inject;
import org.example.newspaperjsclass.dao.ReaderRepository;
import org.example.newspaperjsclass.dao.jdbc.jdbcReaderRepository;
import org.example.newspaperjsclass.dao.model.ReaderEntity;
import org.example.newspaperjsclass.domain.mappers.ReaderMapperService;
import org.example.newspaperjsclass.domain.model.ReadArticleDTO;
import org.example.newspaperjsclass.domain.model.ReaderDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReaderService {

    private final ReaderRepository readerRepository;
    private final jdbcReaderRepository jdbcReaderRepository;
    private final ReaderMapperService readerMapperService;

    public ReaderService(ReaderRepository readerRepository, jdbcReaderRepository jdbcReaderRepository, ReaderMapperService readerMapperService) {
        this.readerRepository = readerRepository;
        this.jdbcReaderRepository = jdbcReaderRepository;
        this.readerMapperService = readerMapperService;
    }

    public List<ReaderDTO> getAllReaders() {
        List<ReaderEntity> readers = readerRepository.getAll();
        return readerMapperService.mapToDTOs(readers);
    }

    public int addReader(ReaderDTO reader) {
        ReaderEntity entity = readerMapperService.mapToEntity(reader);
        return readerRepository.save(entity);
    }

    public void addCredentials(String username, String password, int readerId) {
        jdbcReaderRepository.saveCredentials(username, password, readerId);
    }

    public void deleteReader(int readerId, boolean deleteCredentials) {
        ReaderEntity reader = readerRepository.get(readerId);
        if (deleteCredentials) {
            jdbcReaderRepository.deleteCredentialsByReaderId(readerId);
        }
        readerRepository.delete(reader);
    }

    public ReaderDTO get(int id) {
        ReaderEntity entity = readerRepository.get(id);
        if (entity == null) {
            return null;
        }
        return readerMapperService.mapToDTO(entity);
    }

    public List<ReadArticleDTO> getAllReadersByArticleId(int articleId) {
        return null;
    }
}
