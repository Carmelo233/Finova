package com.finova.finovabackendfileservice.controller;

import com.finova.finovabackendcommon.common.BaseResponse;
import com.finova.finovabackendcommon.common.ResultUtils;
import com.finova.finovabackendfileservice.service.FileService;
import com.finova.finovabackendmodel.result.response.ResultJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传 / 下载文件接口
 */
@RestController
@RequestMapping("/")
public class FileController {
    @Autowired
    private FileService fileService;

    /**
     * 上传单个待分析文件调用接口
     * @param file
     * @param type
     * @return
     */
    @PostMapping("/upload/file")
    public BaseResponse<Integer> uploadFile(@RequestParam MultipartFile file, @RequestParam Integer type) {
        return ResultUtils.success(fileService.handleUploadFile(file, type));
    }

    /**
     * 上传多个待分析文件调用接口
     * @param folder
     * @param type
     * @return
     */
    @PostMapping("/upload/folder")
    public BaseResponse<Integer> uploadFolder(@RequestParam MultipartFile[] folder, @RequestParam Integer type) {
        return ResultUtils.success(fileService.handleUploadFolder(folder, type));
    }
}
