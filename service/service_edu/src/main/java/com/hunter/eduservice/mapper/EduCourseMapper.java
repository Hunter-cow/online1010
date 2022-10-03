package com.hunter.eduservice.mapper;

import com.hunter.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hunter.eduservice.entity.frontVo.CourseWebVo;
import com.hunter.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author Hunter
 * @since 2022-09-09
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    CoursePublishVo getCoursePublish(String courseId);

    CourseWebVo getBaseCourseInfo(String courseId);
}
