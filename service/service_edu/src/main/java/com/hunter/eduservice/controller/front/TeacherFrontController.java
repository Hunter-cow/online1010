package com.hunter.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hunter.commonUtils.ResultVo;
import com.hunter.eduservice.entity.EduCourse;
import com.hunter.eduservice.entity.EduTeacher;
import com.hunter.eduservice.service.EduCourseService;
import com.hunter.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.naming.ldap.PagedResultsControl;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduService/teacherFront")
@CrossOrigin
public class TeacherFrontController {
    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    //分页查询讲师
    @PostMapping("/getTeacherFrontList/{page}/{limit}")
    public ResultVo getTeacherFrontList(@PathVariable long page, @PathVariable long limit) {
        Page<EduTeacher> page1 = new Page<>(page, limit);
        Map<String, Object> map = teacherService.getTeacherFront(page1);

        return ResultVo.ok().data(map);
    }

    @GetMapping("/getTeacherFrontInfo/{id}")
    public ResultVo getTeacherFrontInfo(@PathVariable long id) {
        //根据id查询教师信息
        EduTeacher eduTeacher = teacherService.getById(id);
        //根据id查询教师的课程信息
        LambdaQueryWrapper<EduCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduCourse::getTeacherId, id);
        List<EduCourse> courseList = courseService.list(queryWrapper);


        return ResultVo.ok().data("eduTeacher", eduTeacher).data("courseList", courseList);
    }

}
