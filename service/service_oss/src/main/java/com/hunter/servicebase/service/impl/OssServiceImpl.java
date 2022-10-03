package com.hunter.servicebase.service.impl;

import com.hunter.servicebase.service.OssService;
import com.hunter.servicebase.utlis.ConstantPropertiesUtils;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    public COSClient getCosClient() {
        COSCredentials cred = new BasicCOSCredentials(ConstantPropertiesUtils.SECRET_ID, ConstantPropertiesUtils.SECRET_KEY);
        ClientConfig clientConfig = new ClientConfig(new Region(ConstantPropertiesUtils.REGION));
        COSClient cosClient = new COSClient(cred, clientConfig);
        return cosClient;
    }

    @Override
    public String uploadFileAvatar(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        System.out.println(originalFilename);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String KEY = "/" + new DateTime().toString("yyyy/MM/dd") + "/" + uuid + file.getOriginalFilename();
        System.out.println(KEY);
        String bucket = ConstantPropertiesUtils.PHOTO_BUCKET;
        //根据业务模块，上传至其他存储桶
//        if (type == FileType.INSTRUCTOR) {
//            bucket = photoBucket;
//        } else {
//            bucket = photoBucket;
//        }
        //获得文件名
        String fileName = file.getOriginalFilename();
        //将图片的具体信息传入ObjectMetadate类
        ObjectMetadata meta = new ObjectMetadata();
        //必须设置该属性
        meta.setContentLength(file.getSize());
        //设置字符编码格式
        meta.setContentEncoding("UTF-8");
        //获得文件后缀名并根据传入的图片格式设置ContentType
        if (".png".equals(fileName.lastIndexOf("."))) {
            meta.setContentType("image/png");
        } else if (".jpg".equals(fileName.lastIndexOf("."))) {
            meta.setContentType("image/jpeg");
        }
        //SDK构造方法,具体参造[SDKAPI](https://help.aliyun.com/document_detail/32008.htm?spm=a2c4g.11186623.2.3.65ac605fhxBPgG)
        PutObjectRequest putObjectRequest = null;
        try {
            putObjectRequest = new PutObjectRequest(bucket, KEY, file.getInputStream(), meta);
        } catch (IOException e) {
            e.printStackTrace();
        }
        putObjectRequest.setStorageClass(StorageClass.Standard);
        COSClient client = getCosClient();
        try {
            PutObjectResult putObjectResult = client.putObject(putObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 关闭客户端
        client.shutdown();
        //拼接获得存储桶中可访问的地址
        return "https://" + bucket + ".cos." + ConstantPropertiesUtils.REGION + ".myqcloud.com" + KEY;
    }
}
