package com.hunter.eduservice.controller;


import com.hunter.commonUtils.ResultVo;
import com.hunter.eduservice.client.VodClient;
import com.hunter.eduservice.entity.EduVideo;
import com.hunter.eduservice.service.EduVideoService;
import com.hunter.servicebase.exceptionHandler.ConsumeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author Hunter
 * @since 2022-09-09
 */
@RestController
@RequestMapping("/eduService/eduVideo")
@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;

    //添加小节
    @PostMapping("/addVideo")
    public ResultVo addVideo(@RequestBody EduVideo eduVideo) {
        eduVideoService.save(eduVideo);
        return ResultVo.ok();
    }

    @Autowired
    private VodClient vodClient;

    //删除小节
    // TODO 后面这个方法需要完善，删除小节的时候，同时也要把视频删除
    @DeleteMapping("/deleteVideo/{id}")
    public ResultVo deleteVideo(@PathVariable String id) {
        EduVideo eduVideo = eduVideoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {
            ResultVo resultVo = vodClient.removeAliyunVideoById(videoSourceId);
            if (resultVo.getCode() == 20001) {
                throw new ConsumeException(20001, "删除视频失败");
            }
        }
        eduVideoService.removeById(id);
        return ResultVo.ok();
    }

    //修改小节
    @PostMapping("/updateVideo")
    public ResultVo updateVideo(@RequestBody EduVideo eduVideo) {
        eduVideoService.updateById(eduVideo);
        return ResultVo.ok();
    }

    //根据小节id查询
    @GetMapping("/getVideoById/{videoId}")
    public ResultVo getVideoById(@PathVariable String videoId) {
        EduVideo eduVideo = eduVideoService.getById(videoId);
        return ResultVo.ok().data("video", eduVideo);
    }
}

