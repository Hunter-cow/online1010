package com.hunter.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hunter.eduservice.entity.EduSubject;
import com.hunter.eduservice.entity.excel.SubjectData;
import com.hunter.eduservice.entity.excel.SubjectExcelListener;
import com.hunter.eduservice.entity.vo.OneSubject;
import com.hunter.eduservice.entity.vo.TwoSubject;
import com.hunter.eduservice.mapper.EduSubjectMapper;
import com.hunter.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author Hunter
 * @since 2022-09-08
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {


    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            InputStream is = file.getInputStream();

            EasyExcel.read(is, SubjectData.class, new SubjectExcelListener(eduSubjectService))
                    .sheet().doRead();
        } catch (Exception ex) {

        }

    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //查询所有一级课程
        LambdaQueryWrapper<EduSubject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduSubject::getParentId, "0");
        List<EduSubject> eduSubjects = baseMapper.selectList(queryWrapper);
        //将一级,二级课程封装到eduSubjects里面
        List<OneSubject> oneSubjectList = eduSubjects.stream().map((item) -> {
            OneSubject subject = new OneSubject();
            //查询二级课程
            LambdaQueryWrapper<EduSubject> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(EduSubject::getParentId, item.getId());
            //查询与当前一级课程相关联的二级课程
            List<EduSubject> eduSubjects1 = baseMapper.selectList(queryWrapper1);
            //将eduSubject1转为vo的twoSubject
            List<TwoSubject> twoSubjectList = eduSubjects1.stream().map((item1) -> {
                TwoSubject twoSubject = new TwoSubject();
                twoSubject.setId(item1.getId());
                twoSubject.setTitle(item1.getTitle());
                return twoSubject;
            }).collect(Collectors.toList());
            subject.setId(item.getId());
            subject.setTitle(item.getTitle());
            subject.setChildren(twoSubjectList);
            return subject;
        }).collect(Collectors.toList());

        return oneSubjectList;
    }
}
