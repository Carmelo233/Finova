package com.finova.finovabackendfileservice.service;

import com.finova.finovabackendmodel.result.response.ResultJSON;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface FileService {
    ResultJSON handleUploadFile(MultipartFile file, Integer type);

    ResultJSON handleUploadFolder(MultipartFile[] folder, Integer type);

    ResultJSON handleDownloadFile(String url);

    ResultJSON handleSearchFile(String prefix);

    String getUrlPrefix();

    String handleInnerUploadFile(InputStream inputStream, String fileName);
}
