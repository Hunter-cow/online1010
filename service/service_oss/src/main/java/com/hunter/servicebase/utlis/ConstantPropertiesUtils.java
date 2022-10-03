package com.hunter.servicebase.utlis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "tencent", ignoreInvalidFields = true, ignoreUnknownFields = true)
//加入这两个注解以匹配application.yml
public class ConstantPropertiesUtils {
    public static String APPID;

    public static String SECRET_ID;

    public static String SECRET_KEY;

    public static String REGION;

    public static String PHOTO_BUCKET;

    //注入值
    @Value("${tencent.appId}")
    public void setAppId(String appId) {
        this.APPID = appId;
    }

    @Value("${tencent.secretId}")
    public void setSecretId(String secretId) {
        this.SECRET_ID = secretId;
    }

    @Value("${tencent.secretKey}")
    public void setSecretKey(String secretKey) {
        this.SECRET_KEY = secretKey;
    }

    @Value("${tencent.region}")
    public void setRegion(String region) {
        this.REGION = region;
    }

    @Value("${tencent.photoBucket}")
    public void setPhotoBucket(String photoBucket) {
        this.PHOTO_BUCKET = photoBucket;
    }
}
