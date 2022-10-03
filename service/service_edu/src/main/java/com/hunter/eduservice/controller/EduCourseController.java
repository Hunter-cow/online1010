package com.hunter.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hunter.commonUtils.ResultVo;
import com.hunter.eduservice.entity.EduCourse;
import com.hunter.eduservice.entity.vo.CourseInfoVo;
import com.hunter.eduservice.entity.vo.CoursePublishVo;
import com.hunter.eduservice.entity.vo.CourseQuery;
import com.hunter.eduservice.entity.vo.TeacherQuery;
import com.hunter.eduservice.service.EduCourseService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.EnableTransactionManagement;
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
@RequestMapping("/eduService/eduCourse")
@CrossOrigin
@EnableTransactionManagement
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    @DeleteMapping("/{courseId}")
    public ResultVo removeCourse(@PathVariable String courseId) {
        eduCourseService.removeCourse(courseId);
        return ResultVo.ok().message("删除成功");
    }


    //课程列表 基本实现
    @GetMapping("/getCourseList")
    public ResultVo getCourseList() {
        List<EduCourse> list = eduCourseService.list(null);
        return ResultVo.ok().data("list", list);
    }

    //条件分页查询
    @PostMapping("/pageCourseCondition/{page}/{limit}")
    public ResultVo getCourseListCondition(@PathVariable Long page,
                                           @PathVariable Long limit,
                                           @RequestBody(required = false) CourseQuery courseQuery) {
        Page<EduCourse> pageParam = new Page<>(page, limit);
        eduCourseService.pageQuery(pageParam, courseQuery);
        return ResultVo.ok().data("total", pageParam.getTotal()).data("rows", pageParam.getRecords()).message("获取成功");
    }


    @PostMapping("/addCourseInfo")
    public ResultVo addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        System.out.println(courseInfoVo);
        String id = eduCourseService.saveCourseInfo(courseInfoVo);
        return ResultVo.ok().data("courseId", id);
    }

    @GetMapping("/getCourseInfoById/{courseId}")
    public ResultVo getCourseInfoById(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfo(courseId);

        return ResultVo.ok().data("courseInfoVo", courseInfoVo);
    }

    //修改课程信息
    @PostMapping("/updateCourseInfo")
    public ResultVo updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        eduCourseService.updateCourseInfo(courseInfoVo);
        return ResultVo.ok();
    }

    //根据课程id查询课程确定信息
    @GetMapping("/getPublishCourseInfo/{id}")
    public ResultVo getPublishCourseInfo(@PathVariable String id) {
        System.out.println("id=" + id);
        CoursePublishVo coursePublish = eduCourseService.publishCourseInfo(id);
        return ResultVo.ok().data("publishCourse", coursePublish);
    }

    //课程最终发布
    //修改课程状态
    @PostMapping("publishCourse/{id}")
    public ResultVo publishCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setStatus("Normal"); //设置课程发布状态
        eduCourse.setId(id);
        boolean flag = eduCourseService.updateById(eduCourse);
        if (flag) {
            return ResultVo.ok();
        } else {
            return ResultVo.error();
        }
    }

}

