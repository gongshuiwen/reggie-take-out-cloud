package org.example.reggie.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.reggie.common.R;
import org.example.reggie.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "通用接口")
@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private StorageService storageService;

    @Operation(summary = "下载文件")
    @GetMapping("/download")
    public Resource download(@RequestParam String name) throws Exception {
        return storageService.loadAsResource(name);
    }

    @Operation(summary = "上传文件")
    @PostMapping("/upload")
    public R<String> upload(@RequestBody MultipartFile file) throws Exception {
        return R.success(storageService.store(file));
    }
}
