package com.finova.finovabackendfileservice.service.impl;

import cn.hutool.core.lang.UUID;
import com.finova.finovabackendcommon.utils.AliOssUtil;
import com.finova.finovabackendfileservice.mapper.TaskMapper;
import com.finova.finovabackendfileservice.service.FileService;
import com.finova.finovabackendmodel.domain.Task;
import com.finova.finovabackendmodel.result.Code;
import com.finova.finovabackendmodel.result.ResultJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private AliOssUtil aliOssUtil;
    @Autowired
    private TaskMapper taskMapper;

    /**
     * 上传单个待分析文件
     * @param file
     * @param type
     * @return
     */
    @Override
    public ResultJSON handleUploadFile(MultipartFile file, Integer type) {
        // 判断文件对象是否为空
        if (file == null) {
            // todo 自定义异常
            throw new RuntimeException();
        }
        // 判断分析类型是否正确
        if (type > 5) {
            // todo 1. 设置 type 类型
            // todo 2. 自定义异常
            throw new RuntimeException();
        }
        // 获取当前操作用户 id
        // todo 从 session 重获取当前操作用户 id
        Integer uid = 1;


        // 将文件上传到 AliyunOss，返回文件 url
        String fileUrl;
        try {
            fileUrl = uploadFile(file, null);
        } catch (IOException e) {
            // todo 自定义异常类型，让全局异常处理器处理
            throw new RuntimeException(e);
        }

        // 创建 Task 对象，设置属性
        Task task = Task.builder().uid(uid).fileUrl(fileUrl).type(type).status(0).build();

        // 将 Task 对象保存到数据库，返回 taskId
        taskMapper.insert(task);

        // 返回 taskId
        return new ResultJSON(Code.OK, task.getTaskId());
    }

    /**
     * 上传待分析文件文件夹
     * @param folder
     * @param type
     * @return
     */
    @Override
    public ResultJSON handleUploadFolder(MultipartFile[] folder, Integer type) {

        // 判断是否存在空的文件对象
        if (Arrays.stream(folder).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("数组包含 null 元素");
        }
        // 判断分析类型是否正确
        if (type > 5) {
            // todo 1. 设置 type 类型
            // todo 2. 自定义异常
            throw new RuntimeException();
        }

        // 获取当前操作用户 id
        // todo 从 session 重获取当前操作用户 id
        Integer uid = 1;

        // 将文件上传到 AliyunOss，返回文件 url
        String uuidAsDirPrefix = UUID.randomUUID() + "/";
        // 数据库中存储此文件夹前缀，获取时根据前缀获取而不是准确 url
        String fileUrl = aliOssUtil.getUrlPrefix() + uuidAsDirPrefix;

        // 遍历数组元素，逐个上传文件
        Arrays.stream(folder).forEach(file -> {
            try {
                uploadFile(file, uuidAsDirPrefix);
            } catch (IOException e) {
                // todo 自定义异常处理器
                throw new RuntimeException(e);
            }
        });

        // 创建 Task 对象，设置属性
        Task task = Task.builder().uid(uid).fileUrl(fileUrl).type(type).status(0).build();

        // 将 Task 对象保存到数据库，返回 taskId
        taskMapper.insert(task);

        // 返回 taskId
        return new ResultJSON(Code.OK, task.getTaskId());
    }

    private String uploadFile(MultipartFile file, String prefix) throws IOException {
        // 生成 UUID
        String uuid = String.valueOf(UUID.randomUUID());

        // 获取文件扩展名
        String fileOriginName = file.getOriginalFilename();
        String fileExtensionName = null;
        if (fileOriginName != null) {
            fileExtensionName = fileOriginName.substring(fileOriginName.lastIndexOf(".") - 1);
        }

        // 将文件上传到 AliyunOss，返回文件 url
        String objectName = (prefix == null ? "" : prefix) + uuid + fileExtensionName
        return aliOssUtil.upload(file.getInputStream(), objectName);
    }
}
