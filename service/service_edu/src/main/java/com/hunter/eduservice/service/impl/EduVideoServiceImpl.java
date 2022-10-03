package com.hunter.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hunter.eduservice.client.VodClient;
import com.hunter.eduservice.entity.EduCourse;
import com.hunter.eduservice.entity.EduVideo;
import com.hunter.eduservice.mapper.EduVideoMapper;
import com.hunter.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author Hunter
 * @since 2022-09-09
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    @Override
    public void removeVideoByCourseId(String courseId) {
        //根据courseId删除视频
        LambdaQueryWrapper<EduVideo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduVideo::getCourseId, courseId);
        //查询指定列
        queryWrapper.select(EduVideo::getVideoSourceId);
        List<EduVideo> videoSourceIds = baseMapper.selectList(queryWrapper);
//        List<String> collect = videoSourceIds.stream().map((item) -> {
//            return item.getVideoSourceId();
//        }).collect(Collectors.toList());
        List<String> collect = videoSourceIds.stream().map(EduVideo::getVideoSourceId).filter(Objects::nonNull).collect(Collectors.toList());

        vodClient.removeBatch(collect);


        LambdaQueryWrapper<EduVideo> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(EduVideo::getCourseId, courseId);
        baseMapper.delete(queryWrapper1);
    }
}
