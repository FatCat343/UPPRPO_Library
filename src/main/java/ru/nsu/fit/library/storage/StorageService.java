package ru.nsu.fit.library.storage;

import org.springframework.stereotype.Service;

@Service
public class StorageService {
    private final StorageRepository storageRepository;

    public StorageService(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }


}
