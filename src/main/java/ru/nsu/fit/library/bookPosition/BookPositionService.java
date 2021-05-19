package ru.nsu.fit.library.bookPosition;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookPositionService {
    private final BookPositionRepository bookPositionRepository;

    public BookPositionService(BookPositionRepository bookPositionRepository) {
        this.bookPositionRepository = bookPositionRepository;
    }

    public List<BookPosition> findAllFetch() {
        return bookPositionRepository.findAllFetch();
    }
}
