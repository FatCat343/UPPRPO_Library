package ru.nsu.fit.library.bookPosition;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookPositionService {
    private final BookPositionRepository bookPositionRepository;

    public BookPositionService(BookPositionRepository bookPositionRepository) {
        this.bookPositionRepository = bookPositionRepository;
    }

    public List<BookPosition> findAll() {
        return (List<BookPosition>) bookPositionRepository.findAll();
    }

    public BookPosition save(BookPosition bookPosition) {
        return bookPositionRepository.save(bookPosition);
    }

    public boolean exists(BookPosition bookPosition) {
        return bookPositionRepository.existsBookPositionByStorageAndRackNumberAndShelfNumber(bookPosition.getStorage(),
                bookPosition.getRackNumber(), bookPosition.getShelfNumber());
    }

    public void delete(BookPosition bookPosition) {
        bookPositionRepository.delete(bookPosition);
    }

    public List<BookPosition> findAllFetch() {
        return bookPositionRepository.findAllFetch();
    }

}
