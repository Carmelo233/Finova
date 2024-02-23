package com.finova.finovabackendfileservice;

import com.finova.finovabackendcommon.utils.AliOssUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.InputStream;
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

    @Test
    void testDownload() {
        String url = "a898732d-335b-40bd-899e-dc9bbe90e810.txt";
        InputStream stream = aliOssUtil.downloadAsStream(url);
        System.out.println(stream);

    }

}
