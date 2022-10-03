package com.hunter.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hunter.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hunter.eduservice.entity.frontVo.CourseFrontVo;
import com.hunter.eduservice.entity.frontVo.CourseWebVo;
import com.hunter.eduservice.entity.vo.CourseInfoVo;
import com.hunter.eduservice.entity.vo.CoursePublishVo;
import com.hunter.eduservice.entity.vo.CourseQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Hunter
 * @since 2022-09-09
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo publishCourseInfo(String id);

    void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery);

    void removeCourse(String courseId);

    List<EduCourse> selectCourse();

    //前台多条件分页查询
    Map<String, Object> getCourseFrontInfo(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
