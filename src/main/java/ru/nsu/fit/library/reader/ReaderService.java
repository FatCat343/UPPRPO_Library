package ru.nsu.fit.library.reader;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReaderService {
    private final ReaderRepository readerRepository;

    public ReaderService(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    public List<Reader> findAll(){
        return (List<Reader>) readerRepository.findAll();
    }

    public Reader save(Reader reader){
        return readerRepository.save(reader);
    }

    public void delete(Reader reader){
        readerRepository.delete(reader);
    }

    boolean exist(Reader reader){
        return readerRepository.existsReaderByFirstNameAndLastName(reader.getId(), reader.getFirstName(), reader.getLastName());
    }
}
