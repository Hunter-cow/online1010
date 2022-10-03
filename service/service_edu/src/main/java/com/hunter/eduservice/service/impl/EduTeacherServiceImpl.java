package com.hunter.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hunter.eduservice.entity.EduTeacher;
import com.hunter.eduservice.entity.vo.TeacherQuery;
import com.hunter.eduservice.mapper.EduTeacherMapper;
import com.hunter.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author Hunter
 * @since 2022-09-04
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery) {
        LambdaQueryWrapper<EduTeacher> queryWrapper = new LambdaQueryWrapper<>();
        if (teacherQuery == null) {
            baseMapper.selectPage(pageParam, null);
            return;
        }
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        //判断是否有值
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like(EduTeacher::getName, name);
        }
        if (!StringUtils.isEmpty(level)) {
            queryWrapper.eq(EduTeacher::getLevel, level);
        }
        if (!StringUtils.isEmpty(begin)) {
            //构造条件
            queryWrapper.ge(EduTeacher::getGmtCreate, begin);//ge：大于等于
        }
        if (!StringUtils.isEmpty(begin)) {
            //构造条件
            queryWrapper.le(EduTeacher::getGmtModified, end);//le:小于等于
        }
        queryWrapper.orderByDesc(EduTeacher::getGmtCreate);
        baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Cacheable(value = "banner", key = "'teacher'")
    @Override
    public List<EduTeacher> selectTeacher() {
        LambdaQueryWrapper<EduTeacher> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.orderByDesc(EduTeacher::getId);
        queryWrapper1.last("limit 4");
        List<EduTeacher> teacherList = baseMapper.selectList(queryWrapper1);
        return teacherList;
    }

    @Override
    public Map<String, Object> getTeacherFront(Page<EduTeacher> page1) {

        LambdaQueryWrapper<EduTeacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(EduTeacher::getId);

        baseMapper.selectPage(page1, queryWrapper);
        //分页数据提取到map
        Map<String, Object> map = new HashMap<>();
        List<EduTeacher> records = page1.getRecords();
        long total = page1.getTotal();
        long current = page1.getCurrent();
        long pages = page1.getPages();
        long size = page1.getSize();
        boolean hasNext = page1.hasNext();
        boolean hasPrevious = page1.hasPrevious();
        map.put("records", records);
        map.put("total", total);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }
}
