package com.hunter.eduservice.client;


import com.hunter.commonUtils.ResultVo;
import com.hunter.eduservice.client.Impl.VodClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(value = "service-vod", fallback = VodClientImpl.class)
@Component
public interface VodClient {

    //上传视频到阿里云
    @PostMapping("/eduVod/video/uploadAliyunVideo")
    ResultVo uploadAliyunVideo(@RequestParam(name = "file") MultipartFile file);

    //根据视频id删除1个阿里云视频
    @DeleteMapping("/eduVod/video/removeAliyunVideoById/{id}")
    ResultVo removeAliyunVideoById(@PathVariable String id);

    //根据id删除多个阿里云视频
    @DeleteMapping("/eduVod/video/removeBatch")
    ResultVo removeBatch(@RequestParam("videoIdList") List<String> videoIdList);

    //根据视频id获取视频凭证
    @GetMapping("/getPlayAuth/{id}")
    ResultVo getPlayAuth(@PathVariable("id") String id);


}
