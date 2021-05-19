package ru.nsu.fit.library.bookPosition;

import org.springframework.stereotype.Service;

@Service
public class BookPositionService {
    private final BookPositionRepository bookPositionRepository;

    public BookPositionService(BookPositionRepository bookPositionRepository) {
        this.bookPositionRepository = bookPositionRepository;
    }


}
