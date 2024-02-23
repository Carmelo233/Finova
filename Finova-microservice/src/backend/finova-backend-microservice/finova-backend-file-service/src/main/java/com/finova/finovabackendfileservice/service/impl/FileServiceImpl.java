package com.finova.finovabackendfileservice.service.impl;

import cn.hutool.core.lang.UUID;
import com.finova.finovabackendcommon.common.ErrorCode;
import com.finova.finovabackendcommon.context.BaseContextHandler;
import com.finova.finovabackendcommon.exception.BusinessException;
import com.finova.finovabackendcommon.exception.file.FileUploadException;
import com.finova.finovabackendcommon.exception.file.TaskOperationException;
import com.finova.finovabackendcommon.utils.AliOssUtil;
import com.finova.finovabackendfileservice.service.FileService;
import com.finova.finovabackendmodel.domain.model.Task;
import com.finova.finovabackendmodel.result.response.Code;
import com.finova.finovabackendmodel.result.response.ResultJSON;
import com.finova.finovabackendserviceclient.service.TaskFeignClient;
import feign.FeignException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.finova.finovabackendcommon.constant.AlgConstant.ALG_NUMS;
import static com.finova.finovabackendcommon.constant.AlgConstant.ALG_STATUS_UNANALYSIS;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private AliOssUtil aliOssUtil;
    @Autowired
    private TaskFeignClient taskFeignClient;

    /**
     * 上传单个待分析文件
     * @param file
     * @param type
     * @return
     */
    @Override
    public Integer handleUploadFile(MultipartFile file, Integer type) {
        // 判断文件对象是否为空
        if (file == null) {
            throw new BusinessException(ErrorCode.FILE_NOT_FOUND_ERROR);
        }
        // 判断分析类型是否正确
        if (type > ALG_NUMS) {
            // todo 1. 设置 type 类型
            throw new BusinessException(ErrorCode.ALG_TYPE_ERROR);
        }

        // 从上下文中获取当前操作用户 id
        Integer uid = Integer.valueOf(BaseContextHandler.getUserID());

        // 将文件上传到 AliyunOss，返回文件 url
        String fileUrl;
        try {
            fileUrl = uploadFile(file, null);
        } catch (IOException e) {
            throw new FileUploadException(ErrorCode.FILE_UPLOAD_ERROR);
        }

        // 创建 Task 对象，设置属性
        Task task = Task.builder()
                .uid(uid)
                .fileUrl(fileUrl)
                .type(type)
                .status(ALG_STATUS_UNANALYSIS)
                .build();

        try {
            // 调用 task-service 将 Task 对象保存到数据库，返回 taskId
            return taskFeignClient.createTask(task);
        } catch (Exception e) {
            throw new TaskOperationException(ErrorCode.TASK_CREATE_ERROR);)
        }

    }

    /**
     * 上传待分析文件文件夹
     * @param folder
     * @param type
     * @return
     */
    @Override
    public Integer handleUploadFolder(MultipartFile[] folder, Integer type) {

        // 判断是否存在空的文件对象
        if (Arrays.stream(folder).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("数组包含 null 元素");
        }
        // 判断分析类型是否正确
        if (type > ALG_NUMS) {
            // todo 1. 设置 type 类型
            throw new BusinessException(ErrorCode.ALG_TYPE_ERROR);
        }

        // 获取当前操作用户 id
        Integer uid = Integer.valueOf(BaseContextHandler.getUserID());

        // 将文件上传到 AliyunOss，返回文件 url
        String uuidAsDirPrefix = UUID.randomUUID() + "/";
        // 数据库中存储此文件夹前缀，获取时根据前缀获取而不是准确 url
        String fileUrl = aliOssUtil.getUrlPrefix() + uuidAsDirPrefix;

        // 遍历数组元素，逐个上传文件
        Arrays.stream(folder).forEach(file -> {
            try {
                uploadFile(file, uuidAsDirPrefix);
            } catch (IOException e) {
                throw new FileUploadException(ErrorCode.FILE_UPLOAD_ERROR, e.getMessage());
            }
        });

        // 创建 Task 对象，设置属性
        Task task = Task.builder()
                .uid(uid)
                .fileUrl(fileUrl)
                .type(type)
                .status(ALG_STATUS_UNANALYSIS)
                .build();

        try {
            // 将 Task 对象保存到数据库，返回 taskId
            return taskFeignClient.createTask(task);
        } catch (Exception e) {
            throw new TaskOperationException(ErrorCode.TASK_CREATE_ERROR);)
        }
    }

    /**
     * 处理下载文件的请求
     *
     * @param url 文件的URL地址
     * @return 返回下载文件的结果
     */
    @Override
    public byte[] handleDownloadFile(String url) {
        return aliOssUtil.downloadAsBytes(url);
    }

    /**
     * 上传文件到阿里云OSS
     *
     * @param file 需要上传的文件
     * @param prefix 文件名前缀
     * @return 上传后的文件URL
     * @throws IOException 如果发生IO异常
     */
    private String uploadFile(MultipartFile file, String prefix) throws IOException {
        // 生成 UUID
        String uuid = String.valueOf(UUID.randomUUID());

        // 获取文件扩展名
        String fileOriginName = file.getOriginalFilename();
        String fileExtensionName = null;
        if (fileOriginName != null) {
            fileExtensionName = fileOriginName.substring(fileOriginName.lastIndexOf("."));
        }

        // 将文件上传到 AliyunOss，返回文件 url
        String objectName = (prefix == null ? "" : prefix) + uuid + fileExtensionName;
        return aliOssUtil.upload(file.getInputStream(), objectName);
    }

    /**
     * 处理搜索文件
     *
     * @param prefix 文件前缀
     * @return 搜索结果
     */
    @Override
    public ResultJSON handleSearchFile(String prefix) {
        List<String> fileUrlList = aliOssUtil.listFileUrl(prefix);
        return new ResultJSON(Code.OK, fileUrlList);
    }

    @Override
    public String getUrlPrefix() {
        return aliOssUtil.getUrlPrefix();
    }

    @Override
    public String handleInnerUploadFile(byte[] bytes, String fileName) {
        return aliOssUtil.upload(bytes, fileName);
    }
}
