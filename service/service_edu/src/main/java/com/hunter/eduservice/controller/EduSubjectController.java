package com.hunter.eduservice.controller;


import com.hunter.commonUtils.ResultVo;
import com.hunter.eduservice.entity.vo.OneSubject;
import com.hunter.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author Hunter
 * @since 2022-09-08
 */
@RestController
@RequestMapping("/eduService/subject")
@CrossOrigin
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;

    @PostMapping("/addSubject")
    public ResultVo addSubject(@RequestParam(value = "file") MultipartFile file) {
        try {
            eduSubjectService.saveSubject(file, eduSubjectService);
            return ResultVo.ok().message("上传成功");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResultVo.error().message("上传失败");
    }

    @GetMapping("/getAllSubject")
    public ResultVo getAllSubject() {
        List<OneSubject> list = eduSubjectService.getAllOneTwoSubject();
        return ResultVo.ok().data("list", list);
    }

}

