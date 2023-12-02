package com.finova.finovabackendmodel.domain.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
public class Task {

    /**
     * 本次任务的 id
     */
    private Integer taskId;

    /**
     * 本次任务所属用户 id
     */
    private Integer uid;

    /**
     * 待分析文件在 AliyunOss 存储的 url
     */
    private String fileUrl;

    /**
     * 调用算法类型
     */
    private Integer type;

    /**
     * 执行状态
     */
    private Integer status;

    /**
     * 生成结果文件存储路径
     */
    private String resultUrl;

    /**
     * 上传时间
     */
    private LocalDateTime uploadTime;

    /**
     * 状态更新时间
     */
    private LocalDateTime updateTime;
}
