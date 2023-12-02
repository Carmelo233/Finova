package com.finova.finovabackendfileservice;

import com.finova.finovabackendcommon.utils.AliOssUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class FinovaBackendFileserviceApplicationTests {
    @Autowired
    private AliOssUtil aliOssUtil;
    @Test
    void contextLoads() {
        String urlPrefix = aliOssUtil.getUrlPrefix();
        System.out.println(urlPrefix);
        String prefix = "http://node-picture.oss-cn-guangzhou.aliyuncs.com";
        List<String> fileUrl = aliOssUtil.listFileUrl("2023");
        System.out.println(fileUrl);
    }

}
