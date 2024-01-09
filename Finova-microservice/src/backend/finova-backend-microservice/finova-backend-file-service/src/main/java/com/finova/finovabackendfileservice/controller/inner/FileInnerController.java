package com.finova.finovabackendfileservice.controller.inner;

import com.finova.finovabackendfileservice.service.FileService;
import com.finova.finovabackendmodel.result.response.ResultJSON;
import com.finova.finovabackendserviceclient.service.FileFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 微服务内部 获取文件 / 上传结果接口
 */
@RequestMapping("/inner")
@RestController
public class FileInnerController implements FileFeignClient {

    @Autowired
    private FileService fileService;


    @PostMapping("/upload/file")
    public String uploadFile(@RequestParam InputStream inputStream, String fileName) {
        return fileService.handleInnerUploadFile(inputStream, fileName);
    }

    /**
     * 下载文件
     *
     * @param url 文件的URL
     * @return 返回ResultJSON对象
     */
    @GetMapping("/download/file")
    public ResultJSON downloadFile(@RequestParam String url) {
        return fileService.handleDownloadFile(url);
    }

    /**
     * 通过前缀搜索文件
     * @param prefix 文件前缀
     * @return 文件搜索结果
     */
    @GetMapping("/search-file-with-prefix")
    public ResultJSON searchFileWithPrefix(@RequestParam String prefix) {
        return fileService.handleSearchFile(prefix);
    }

    @GetMapping("/get-file-url-prefix")
    public String getFileUrlPrefix() {
        return fileService.getUrlPrefix();
    }
}
