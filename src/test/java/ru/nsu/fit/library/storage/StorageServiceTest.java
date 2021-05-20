package ru.nsu.fit.library.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.nsu.fit.library.library.Library;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class StorageServiceTest {

    @Autowired
    private StorageService service;

    @MockBean
    private StorageRepository repository;

    @Test
    @DisplayName("Test findAll()")
    void findAll() {
        Library lb1 = new Library(1, "7th Avenue, 14");
        Storage s1 = new Storage(1, lb1, 1);
        Storage s2 = new Storage(2, lb1, 2);
        Storage s3 = new Storage(3, lb1, 3);
        doReturn(Arrays.asList(s1, s2, s3)).when(repository).findAll();
        List<Storage> storageList = service.findAll();
        Assertions.assertEquals(3, storageList.size(), "findAll() should return 3 storages");
    }

    @Test
    @DisplayName("Test save()")
    void save() {
        Library lb1 = new Library(1, "7th Avenue, 14");
        Storage storage = new Storage(1, lb1, 1);
        doReturn(storage).when(repository).save(any());
        Storage saved = service.save(storage);
        Assertions.assertNotNull(saved, "saved storage should not be null");
    }

    @Test
    @DisplayName("Test exists()")
    void exists() {
        Library lb1 = new Library(1, "7th Avenue, 14");
        Storage storage = new Storage(1, lb1, 1);
        doReturn(true).when(repository).existsDistinctByLibraryAndRoomNumber(any(), any());
        service.exist(storage);
        verify(repository, times(1))
                .existsDistinctByLibraryAndRoomNumber(storage.getLibrary(), storage.getRoomNumber());
    }

    @Test
    @DisplayName("Test delete()")
    void delete() {
        Library lb1 = new Library(1, "7th Avenue, 14");
        Storage storage = new Storage(1, lb1, 1);
        doNothing().when(repository).delete(storage);
        service.delete(storage);
        verify(repository, times(1)).delete(storage);
    }
}
