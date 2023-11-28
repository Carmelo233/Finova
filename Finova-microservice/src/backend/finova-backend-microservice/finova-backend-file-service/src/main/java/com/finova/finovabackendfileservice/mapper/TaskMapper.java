package com.finova.finovabackendfileservice.mapper;

import com.finova.finovabackendmodel.domain.Task;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaskMapper {

    /**
     * 创建任务
     * @param task
     */
    void insert(Task task);
}
