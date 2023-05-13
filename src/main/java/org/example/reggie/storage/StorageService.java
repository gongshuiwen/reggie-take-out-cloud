package org.example.reggie.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    String store(MultipartFile file) throws IOException;

    Resource loadAsResource(String filename) throws IOException;

    Stream<Path> loadAll();

    Path load(String filename);

    void deleteAll();
}
