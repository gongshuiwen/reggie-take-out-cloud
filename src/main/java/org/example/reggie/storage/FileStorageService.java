package org.example.reggie.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.util.UUID;
import java.util.stream.Stream;

@Slf4j
@Service
public class FileStorageService implements StorageService{

    private static final String basePath = "data/";

    @Override
    public void init() {

    }

    @Override
    public String store(MultipartFile file) throws IOException {
        // 获取文件名后缀
        String suffix = "";
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null) {
//            String[] temp = fileName.split("\\.");
//            if (temp.length > 1) {
//                suffix = "." + temp[temp.length - 1];
//            }
            int index = originalFilename.lastIndexOf(".");
            if (index >= 0) {
                suffix = originalFilename.substring(index);
            }
        }

        // 使用 UUID 重新生成文件名，防止用户上传重名的文件时文件被覆盖
        String fileName = UUID.randomUUID() + suffix;
//        File f = new File(basePath + fileName);
//        boolean res = f.createNewFile();
//        int length;
//        byte[] buff = new byte[1024 * 1024 * 2];
//        try (InputStream is = file.getInputStream();
//             OutputStream os = Files.newOutputStream(f.toPath());
//        ) {
//            while ((length = is.read(buff)) != -1) {
//                os.write(buff, 0 , length);
//            }
//        }
        File f = new File(basePath + fileName);
        file.transferTo(f.getAbsoluteFile());
        return fileName;
    }

    @Override
    public Resource loadAsResource(String filename){
        return new FileSystemResource(basePath + filename);
    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public Path load(String filename) {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}
