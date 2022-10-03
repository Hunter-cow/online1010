package com.hunter.servicebase.controller;

import com.hunter.commonUtils.ResultVo;
import com.hunter.servicebase.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduOss/fileOss")
@CrossOrigin
public class OssController {
    @Autowired
    private OssService ossService;

    @PostMapping("/upload")
    public ResultVo uploadFile(@RequestParam(value = "file") MultipartFile file) {
        String url = ossService.uploadFileAvatar(file);
        return ResultVo.ok().data("url", url).message("文件上传成功");
    }

}
