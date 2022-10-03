package com.hunter.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hunter.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hunter.eduservice.entity.vo.TeacherQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author Hunter
 * @since 2022-09-04
 */
public interface EduTeacherService extends IService<EduTeacher> {
    void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery);

    List<EduTeacher> selectTeacher();

    Map<String, Object> getTeacherFront(Page<EduTeacher> page1);
}
