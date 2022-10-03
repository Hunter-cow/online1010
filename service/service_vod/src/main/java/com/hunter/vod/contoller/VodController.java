package com.hunter.vod.contoller;

import com.hunter.commonUtils.ResultVo;
import com.hunter.servicebase.exceptionHandler.ConsumeException;
import com.hunter.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduVod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    //上传视频到阿里云
    @PostMapping("/uploadAliyunVideo")
    public ResultVo uploadAliyunVideo(@RequestParam(name = "file") MultipartFile file) {
        //返回上传视频的id
        String videoId = vodService.uploadVideoAliyun(file);
        return ResultVo.ok().data("videoId", videoId);
    }


    //根据视频id删除1个阿里云视频
    @DeleteMapping("/removeAliyunVideoById/{id}")
    public ResultVo removeAliyunVideoById(@PathVariable String id) {
        vodService.removeAliyunVideoById(id);
        return ResultVo.ok();
    }

    //根据id删除多个阿里云视频
    @DeleteMapping("/removeBatch")
    public ResultVo removeBatch(@RequestParam("videoIdList") List<String> videoIdList) {
        vodService.removeMoreVideo(videoIdList);
        return ResultVo.ok();
    }

    //根据视频id获取视频凭证
    @GetMapping("/getPlayAuth/{id}")
    public ResultVo getPlayAuth(@PathVariable String id) {
        try {
            String playAuth = vodService.getPlayAuth(id);
            return ResultVo.ok().data("playAuth", playAuth);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConsumeException(20001, "获取视频凭证失败");
        }
    }


}
