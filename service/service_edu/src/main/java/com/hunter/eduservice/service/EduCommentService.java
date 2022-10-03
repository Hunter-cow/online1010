package com.hunter.eduService.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hunter.eduservice.entity.EduComment;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author Hunter
 * @since 2022-09-26
 */
public interface EduCommentService extends IService<EduComment> {

    Map<String, Object> getPageComment(Page<EduComment> page1,String courseId);
}
