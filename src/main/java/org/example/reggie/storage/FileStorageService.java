package org.example.reggie.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
public class FileStorageService implements StorageService, InitializingBean {

    private static final String DEFAULT_BASE_PATH = "./data/";

    private File basePath;

    @Autowired
    public void setBasePath(@Value("${reggie.base-path:}") String path) {
        if (path == null || path.isEmpty()) {
            this.basePath = new File(DEFAULT_BASE_PATH);
        } else {
            this.basePath = new File(path);
        }
    }

    @Override
    public void init() throws IOException {
        log.info("Files Stored in: {}", basePath.getCanonicalPath());
    }

    @Override
    public String store(MultipartFile multipartFile) throws IOException {
        // 使用 UUID + 文件类型后缀重新生成文件名，防止用户上传重名的文件时文件被覆盖
        String filename = UUID.randomUUID() + getFilenameSuffix(multipartFile.getOriginalFilename());
        File file = new File(basePath, filename);
        multipartFile.transferTo(file.getAbsoluteFile());
        return filename;
    }

    @Override
    public Resource loadAsResource(String filename){
        return new FileSystemResource(new File(basePath, filename));
    }

    @Override
    public void afterPropertiesSet() throws IOException {
        init();
    }

    private String getFilenameSuffix(String filename) {
        String suffix = "";
        if (filename != null) {
            int index = filename.lastIndexOf(".");
            if (index >= 0) {
                suffix = filename.substring(index);
            }
        }
        return suffix;
    }
}
