package ru.nsu.fit.library.library;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {
    private final LibraryRepository libraryRepository;

    public LibraryService(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    public List<Library> findAll() {
        return (List<Library>) libraryRepository.findAll();
    }

    public void delete(Library library) {
        libraryRepository.delete(library);
    }

    public Library save(Library library) {
        return libraryRepository.save(library);
    }

    public boolean exist(Library library) {
        return libraryRepository.existsLibraryByAddress(library.getAddress());
    }

}
