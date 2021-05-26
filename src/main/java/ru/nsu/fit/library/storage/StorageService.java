package ru.nsu.fit.library.storage;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageService {
    private final StorageRepository storageRepository;

    public StorageService(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }

    public List<Storage> findAll(){
        return storageRepository.findAll();
    }

    public Storage save(Storage storage) {
        return storageRepository.save(storage);
    }

    public void delete(Storage storage) {
        storageRepository.delete(storage);
    }

    public boolean exist(Storage storage) {
        return storageRepository.existsDistinctByLibraryAndRoomNumber(storage.getLibrary(), storage.getRoomNumber());
    }

}
