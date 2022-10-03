package com.hunter.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hunter.eduService.service.EduCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hunter.eduservice.entity.EduComment;
import com.hunter.eduservice.mapper.EduCommentMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author Hunter
 * @since 2022-09-26
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {


    @Override
    public Map<String, Object> getPageComment(Page<EduComment> page1, String courseId) {
        LambdaQueryWrapper<EduComment> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(courseId)) {
            queryWrapper.eq(EduComment::getCourseId, courseId);
        }
        queryWrapper.orderByDesc(EduComment::getGmtCreate);
        baseMapper.selectPage(page1, queryWrapper);
        long total = page1.getTotal();//总记录数
        List<EduComment> commentList = page1.getRecords();//数据集合
        long size = page1.getSize();//每页记录数
        long current = page1.getCurrent();//当前页
        long pages = page1.getPages();//总页数
        boolean hasPrevious = page1.hasPrevious();//是否有上一页
        boolean hasNext = page1.hasNext();//是否有下一页
        Map<String, Object> map = new HashMap<>();
        map.put("list", commentList);
        map.put("size", size);
        map.put("current", current);
        map.put("pages", pages);
        map.put("hasPrevious", hasPrevious);
        map.put("hasNext", hasNext);
        map.put("total", total);
        return map;
    }
}
