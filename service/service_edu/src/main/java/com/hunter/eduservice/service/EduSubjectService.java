package com.hunter.eduservice.service;

import com.hunter.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hunter.eduservice.entity.vo.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author Hunter
 * @since 2022-09-08
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file, EduSubjectService eduSubjectService);

    List<OneSubject> getAllOneTwoSubject();
}
