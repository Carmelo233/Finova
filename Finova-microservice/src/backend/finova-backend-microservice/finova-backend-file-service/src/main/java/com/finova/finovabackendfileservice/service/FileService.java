package com.finova.finovabackendfileservice.service;

import com.finova.finovabackendmodel.result.response.ResultJSON;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface FileService {
    Integer handleUploadFile(MultipartFile file, Integer type);

    Integer handleUploadFolder(MultipartFile[] folder, Integer type);

    byte[] handleDownloadFile(String url);

    ResultJSON handleSearchFile(String prefix);

    String getUrlPrefix();

    String handleInnerUploadFile(byte[] bytes, String fileName);
}
