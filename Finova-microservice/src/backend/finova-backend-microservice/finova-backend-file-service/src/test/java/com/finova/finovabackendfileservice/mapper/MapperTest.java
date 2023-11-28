package com.finova.finovabackendfileservice.mapper;

import com.finova.finovabackendmodel.domain.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MapperTest {
    @Autowired
    private TaskMapper taskMapper;

    @Test
    public void testInsert() {
        Task task = Task.builder().uid(1).fileUrl("fileUrl").type(-1).status(0).build();
        taskMapper.insert(task);
        System.out.println(task.getTaskId());
    }
}
