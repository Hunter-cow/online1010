package com.hunter.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hunter.eduservice.entity.EduChapter;
import com.hunter.eduservice.entity.EduVideo;
import com.hunter.eduservice.entity.vo.ChapterVo;
import com.hunter.eduservice.entity.vo.VideoVo;
import com.hunter.eduservice.mapper.EduChapterMapper;
import com.hunter.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hunter.eduservice.service.EduVideoService;
import com.hunter.servicebase.exceptionHandler.ConsumeException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Hunter
 * @since 2022-09-09
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        LambdaQueryWrapper<EduChapter> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduChapter::getCourseId, courseId);
        queryWrapper.orderByAsc(EduChapter::getGmtCreate);
        List<EduChapter> eduChapters = baseMapper.selectList(queryWrapper);
        //查询小节信息
        LambdaQueryWrapper<EduVideo> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(EduVideo::getCourseId, courseId);
        queryWrapper1.orderByAsc(EduVideo::getGmtCreate);
        List<EduVideo> videos = eduVideoService.list(queryWrapper1);

        //1.先将所有的章节封装
        List<ChapterVo> list = eduChapters.stream().map((item) -> {
            ChapterVo chapterVo = new ChapterVo();
            chapterVo.setId(item.getId());
            chapterVo.setTitle(item.getTitle());
//            List<VideoVo> collect = videos.stream().map((item1) -> {
//                VideoVo videoVo = new VideoVo();
//                if (item.getId().equals(item1.getChapterId())) {
//                    BeanUtils.copyProperties(item1, videoVo);
//                    return videoVo;
//                }
//                return null;
//            }).collect(Collectors.toList());
            List<VideoVo> children = new ArrayList<>();
            videos.forEach((item1) -> {
                if (item.getId().equals(item1.getChapterId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(item1, videoVo);
                    children.add(videoVo);
                }
            });
            chapterVo.setChildren(children);

            return chapterVo;
        }).collect(Collectors.toList());


        return list;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        LambdaQueryWrapper<EduVideo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduVideo::getChapterId, chapterId);
        int count = eduVideoService.count(queryWrapper);
        if (count > 0) {
            throw new ConsumeException(20001, "该课程含有小节内容，不能删除");
        } else {
            //不能查询出小节，进行删除
            int delete = baseMapper.deleteById(chapterId);
            return delete > 0;
        }
    }

    @Override
    public void removeChapterByCourseId(String courseId) {
        LambdaQueryWrapper<EduChapter> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduChapter::getCourseId, courseId);
        baseMapper.delete(queryWrapper);
    }
}
