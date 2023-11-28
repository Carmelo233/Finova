package com.finova.finovabackendfileservice.service;

import com.finova.finovabackendmodel.result.ResultJSON;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    ResultJSON handleUploadFile(MultipartFile file, Integer type);

    ResultJSON handleUploadFolder(MultipartFile[] folder, Integer type);
}
