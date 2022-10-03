package com.hunter.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hunter.eduservice.client.VodClient;
import com.hunter.eduservice.entity.EduCourse;
import com.hunter.eduservice.entity.EduCourseDescription;
import com.hunter.eduservice.entity.frontVo.CourseFrontVo;
import com.hunter.eduservice.entity.frontVo.CourseWebVo;
import com.hunter.eduservice.entity.vo.CourseInfoVo;
import com.hunter.eduservice.entity.vo.CoursePublishVo;
import com.hunter.eduservice.entity.vo.CourseQuery;
import com.hunter.eduservice.mapper.EduCourseMapper;
import com.hunter.eduservice.service.EduChapterService;
import com.hunter.eduservice.service.EduCourseDescriptionService;
import com.hunter.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hunter.eduservice.service.EduVideoService;
import com.hunter.servicebase.exceptionHandler.ConsumeException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Hunter
 * @since 2022-09-09
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private EduCourseService eduCourseService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //1.向课程表添加信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert <= 0) {
            throw new ConsumeException(20001, "添加课程信息失败");
        }
        //获取课程id
        String cid = eduCourse.getId();
        //2.向课程简介表插入信息
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo, eduCourseDescription);
        eduCourseDescription.setId(cid);
        eduCourseDescriptionService.save(eduCourseDescription);
        return cid;
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {

        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);

        //课程描述
        EduCourseDescription description = eduCourseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(description.getDescription());

        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update <= 0) {
            throw new ConsumeException(20001, "修改课程信息失败");
        }
//        String id = eduCourse.getId();
//        String description = courseInfoVo.getDescription();
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo, eduCourseDescription);
//        eduCourseDescription.setId(id);
//        eduCourseDescription.setDescription(description);
        eduCourseDescriptionService.updateById(eduCourseDescription);
    }

    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        CoursePublishVo coursePublishVo = baseMapper.getCoursePublish(id);
        System.out.println("coursePublishVo" + coursePublishVo);
        return coursePublishVo;
    }

    @Override
    public void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery) {
        LambdaQueryWrapper<EduCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(EduCourse::getGmtCreate);
        if (courseQuery == null) {
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }
        //取出值，判断他们是否有值
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();

        //判断条件值是否为空，如果不为空，拼接条件
        //判断是否有传入教师名
        if (!StringUtils.isEmpty(title)) {
            //构建条件
            queryWrapper.like(EduCourse::getTitle, title);//参数1：数据库字段名； 参数2：模糊查询的值
        }
        //判断是否传入状态
        if (!StringUtils.isEmpty(status)) {
            //构造条件
            queryWrapper.eq(EduCourse::getStatus, status);
        }
        baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Autowired
    private VodClient vodClient;

    @Override
    public void removeCourse(String courseId) {

        //根据课程id删除小节
        eduVideoService.removeVideoByCourseId(courseId);

        //根据课程id删除章节
        eduChapterService.removeChapterByCourseId(courseId);
        //根据课程id删除描述
        eduCourseDescriptionService.removeById(courseId);
        //根据课程id删除课程
        eduCourseService.removeById(courseId);
    }

    @Cacheable(value = "banner", key = "'course'")
    @Override
    public List<EduCourse> selectCourse() {
        LambdaQueryWrapper<EduCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(EduCourse::getViewCount);
        queryWrapper.last("limit 8");
        List<EduCourse> courseList = baseMapper.selectList(queryWrapper);
        return courseList;
    }

    @Override
    public Map<String, Object> getCourseFrontInfo(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo) {
        String title = null;
        String subjectId = null;
        String subjectParentId = null;
        String gmtCreateSort = null;
        String buyCountSort = null;
        String priceSort = null;
        String teacherId = null;
        if (!StringUtils.isEmpty(courseFrontVo)) {
            title = courseFrontVo.getTitle();
            subjectId = courseFrontVo.getSubjectId();
            subjectParentId = courseFrontVo.getSubjectParentId();
            gmtCreateSort = courseFrontVo.getGmtCreateSort();
            buyCountSort = courseFrontVo.getBuyCountSort();
            priceSort = courseFrontVo.getPriceSort();
            teacherId = courseFrontVo.getTeacherId();
        }
        LambdaQueryWrapper<EduCourse> queryWrapper = new LambdaQueryWrapper<>();
        //判断条件值是否为空，不为空拼接条件
        if (!StringUtils.isEmpty(subjectParentId)) {//一级分类
            queryWrapper.eq(EduCourse::getSubjectParentId, subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)) {//二级分类
            queryWrapper.eq(EduCourse::getSubjectId, subjectId);
        }
        if (!StringUtils.isEmpty(buyCountSort)) {//关注度
            queryWrapper.orderByDesc(EduCourse::getBuyCount);
        }
        if (!StringUtils.isEmpty(priceSort)) {//价格
            queryWrapper.orderByDesc(EduCourse::getPrice);
        }
        if (!StringUtils.isEmpty(gmtCreateSort)) {//最新，创建时间
            queryWrapper.orderByDesc(EduCourse::getGmtCreate);
        }
        baseMapper.selectPage(pageCourse, queryWrapper);
        long total = pageCourse.getTotal();//总记录数
        List<EduCourse> courseList = pageCourse.getRecords();//数据集合
        long size = pageCourse.getSize();//每页记录数
        long current = pageCourse.getCurrent();//当前页
        long pages = pageCourse.getPages();//总页数
        boolean hasPrevious = pageCourse.hasPrevious();//是否有上一页
        boolean hasNext = pageCourse.hasNext();//是否有下一页

        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("list", courseList);
        map.put("size", size);
        map.put("current", current);
        map.put("pages", pages);
        map.put("hasPrevious", hasPrevious);
        map.put("hasNext", hasNext);


        return map;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
