package com.finova.finovabackendcommon.utils;


import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Slf4j
public class AliOssUtil {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

    private String urlPrefix;

    public AliOssUtil(String endpoint, String accessKeyId, String accessKeySecret, String bucketName) {
        this.endpoint = endpoint;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.bucketName = bucketName;
    }

    /**
     * 获取路径前缀
     * @return
     */
    public String getUrlPrefix() {
        if (urlPrefix == null) {
            urlPrefix = "https://" + bucketName + "." + endpoint + "/";
        }
        return urlPrefix;
    }

    /**
     * 文件上传（字节数组）
     *
     * @param bytes
     * @param objectName
     * @return
     */
    public String upload(byte[] bytes, String objectName) {
        return upload(new ByteArrayInputStream(bytes), objectName);
    }

    /**
     * 文件上传（输入流）
     * @param inputStream
     * @param objectName
     * @return
     */
    public String upload(InputStream inputStream, String objectName) {
        // 创建 OSSClient 实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 创建 PutObject 请求
            ossClient.putObject(bucketName, objectName, inputStream);
        } catch (OSSException oe) {
            log.error("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            log.error("Error Message:" + oe.getErrorMessage());
            log.error("Error Code:" + oe.getErrorCode());
            log.error("Request ID:" + oe.getRequestId());
            log.error("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            // todo 更新此处的异常类型
            log.error("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            log.error("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

        //文件访问路径规则 https://BucketName.Endpoint/ObjectName
        String fileUrl = getUrlPrefix() + objectName;

        log.info("文件上传到:{}", fileUrl);

        return fileUrl;
    }

    /**
     * 下载指定的OSS对象到本地文件。
     *
     * @param objectName OSS对象的名称
     */
    public InputStream downloadAsStream(String objectName) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        InputStream objectInputStream = null;
        try {
            // 通过GetObject方法下载文件。
            GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, objectName);
            objectInputStream = ossClient.getObject(getObjectRequest).getObjectContent();
            log.info("文件流获取成功");
        } catch (OSSException oe) {
            log.error("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            log.error("Error Message:" + oe.getErrorMessage());
            log.error("Error Code:" + oe.getErrorCode());
            log.error("Request ID:" + oe.getRequestId());
            log.error("Host ID:" + oe.getHostId());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return objectInputStream;
    }

    // todo 待完善
    public List<String> listFileUrl(String prefix) {
        List<String> res = new ArrayList<>();

        // 构造ListObjectsRequest请求
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);

        // 设置prefix参数来获取fun目录下的所有文件。
        listObjectsRequest.setPrefix(prefix);

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 列出文件。
        ObjectListing listing = ossClient.listObjects(listObjectsRequest);

        // 遍历所有文件。
        for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
            res.add(objectSummary.getKey());
            //文件访问路径
//            Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 100);
//            URL url = ossClient.generatePresignedUrl(bucketName, objectSummary.getKey(), expiration);
//            res.add(url.toString());
        }
        // 关闭OSSClient。
        ossClient.shutdown();
        return res;
    }
}
