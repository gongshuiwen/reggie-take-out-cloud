package org.example.reggie.controller;

import org.example.reggie.common.R;
import org.example.reggie.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private StorageService storageService;

    @GetMapping("/download")
    public Resource download(@RequestParam String name) throws IOException {
        return storageService.loadAsResource(name);
    }

    @PostMapping("/upload")
    public R<String> upload(@RequestBody MultipartFile file) throws IOException {
        return R.success(storageService.store(file));
    }
}
