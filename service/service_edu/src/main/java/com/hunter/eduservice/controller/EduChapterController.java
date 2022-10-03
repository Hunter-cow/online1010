package com.hunter.eduservice.controller;


import com.hunter.commonUtils.ResultVo;
import com.hunter.eduservice.entity.EduChapter;
import com.hunter.eduservice.entity.vo.ChapterVo;
import com.hunter.eduservice.service.EduChapterService;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Hunter
 * @since 2022-09-09
 */
@RestController
@RequestMapping("/eduService/eduChapter")
@CrossOrigin
public class EduChapterController {
    @Autowired
    private EduChapterService eduChapterService;

    @GetMapping("/getChapterVideo/{courseId}")
    public ResultVo getChapterVideo(@PathVariable String courseId) {
        List<ChapterVo> list = eduChapterService.getChapterVideoByCourseId(courseId);
        return ResultVo.ok().data("allChapterVideo", list);
    }

    //添加章节
    @PostMapping("/addChapter")
    public ResultVo addChapter(@RequestBody EduChapter eduChapter) {
        System.out.println(eduChapter);
        eduChapterService.save(eduChapter);
        return ResultVo.ok();
    }

    //根据章节id查询
    @GetMapping("/getChapter/{chapterId}")
    public ResultVo getChapter(@PathVariable String chapterId) {
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return ResultVo.ok().data("chapter", eduChapter);
    }

    //修改章节
    @PostMapping("/updateChapter")
    public ResultVo updateChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.updateById(eduChapter);
        return ResultVo.ok();
    }

    //删除章节
    @DeleteMapping("/deleteById/{chapterId}")
    public ResultVo deleteById(@PathVariable String chapterId) {
        boolean flag = eduChapterService.deleteChapter(chapterId);
        if (flag) {
            return ResultVo.ok();
        } else {
            return ResultVo.error();
        }

    }

}

