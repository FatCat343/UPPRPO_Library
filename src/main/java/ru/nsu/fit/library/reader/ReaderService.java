package ru.nsu.fit.library.reader;

import org.springframework.beans.factory.annotation.Autowired;
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

    public void save(Reader reader){
        readerRepository.save(reader);
    }

    public void delete(Reader reader){
        readerRepository.delete(reader);
    }
}
