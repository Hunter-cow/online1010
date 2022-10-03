package com.hunter.eduservice.service;

import com.hunter.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hunter.eduservice.entity.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Hunter
 * @since 2022-09-09
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);

    void removeChapterByCourseId(String courseId);
}
