package com.hunter.eduservice.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hunter.commonUtils.ResultVo;
import com.hunter.eduservice.entity.EduCourse;
import com.hunter.eduservice.entity.EduTeacher;
import com.hunter.eduservice.service.EduCourseService;
import com.hunter.eduservice.service.EduTeacherService;
import org.bouncycastle.cms.PasswordRecipient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduService/indexFront")
@CrossOrigin
public class IndexFrontController {
    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduTeacherService eduTeacherService;


    //查询前四条名师,前8课程
    @GetMapping("/index")
    public ResultVo index() {
        List<EduCourse> courseList = eduCourseService.selectCourse();
        List<EduTeacher> teacherList = eduTeacherService.selectTeacher();

        return ResultVo.ok().data("eduList", courseList).data("teacherList", teacherList);
    }

}
