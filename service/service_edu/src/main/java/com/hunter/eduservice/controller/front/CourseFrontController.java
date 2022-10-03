package com.hunter.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hunter.commonUtils.CourseWebVoOrder;
import com.hunter.commonUtils.ResultVo;
import com.hunter.eduservice.entity.EduCourse;
import com.hunter.eduservice.entity.frontVo.CourseFrontVo;
import com.hunter.eduservice.entity.frontVo.CourseWebVo;
import com.hunter.eduservice.entity.vo.ChapterVo;
import com.hunter.eduservice.service.EduChapterService;
import com.hunter.eduservice.service.EduCourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduService/courseFront")
@CrossOrigin
@Slf4j
public class CourseFrontController {
    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private EduChapterService eduChapterService;

    //课程详情的方法
    @GetMapping("/getFrontCourseInfo/{courseId}")
    public ResultVo getFrontCourseInfo(@PathVariable String courseId) {
        //根据课程id，编写sql语句查询课程信息
        CourseWebVo courseWebVo = eduCourseService.getBaseCourseInfo(courseId);
        //根据课程id,查询章节小节
        List<ChapterVo> chapterVideoByCourseId = eduChapterService.getChapterVideoByCourseId(courseId);

        return ResultVo.ok().data("courseWebVo", courseWebVo).data("chapterVideoByCourseId", chapterVideoByCourseId);
    }


    //带条件的分页查询课程（前台）
    @PostMapping("/getFrontCourseList/{page}/{limit}")
    public ResultVo getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                       @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<EduCourse> page1 = new Page<>(page, limit);

        Map<String, Object> courseFrontInfo = eduCourseService.getCourseFrontInfo(page1, courseFrontVo);
        log.info("courseFrontInfo{}", courseFrontInfo);
        return ResultVo.ok().data(courseFrontInfo);
    }


    @PostMapping("/getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable("id") String id) {
        CourseWebVo baseCourseInfo = eduCourseService.getBaseCourseInfo(id);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(baseCourseInfo, courseWebVoOrder);
        return courseWebVoOrder;
    }
}
