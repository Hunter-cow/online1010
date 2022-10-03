package com.hunter.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hunter.commonUtils.ResultVo;
import com.hunter.eduservice.entity.EduTeacher;
import com.hunter.eduservice.entity.vo.TeacherQuery;
import com.hunter.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author Hunter
 * @since 2022-09-04
 */
@RestController
@RequestMapping("/eduService/eduTeacher")
@CrossOrigin
public class EduTeacherController {
    @Autowired
    private EduTeacherService teacherService;

    @GetMapping("/findAll")
    public ResultVo findAllTeacher() {
        List<EduTeacher> list = teacherService.list(null);
        return ResultVo.ok().data("items", list);
    }

    //逻辑删除
    @DeleteMapping("/{id}")
    public ResultVo removeTeacher(@PathVariable String id) {
        boolean bool = teacherService.removeById(id);
        if (bool) {
            return ResultVo.ok();
        } else {
            return ResultVo.error();
        }
    }

    @GetMapping("/pageTeacher/{current}/{size}")
    public ResultVo pageListTeacher(@PathVariable long current, @PathVariable int size) {
        Page<EduTeacher> pageTeacher = new Page<EduTeacher>(current, size);
        teacherService.page(pageTeacher, null);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return ResultVo.ok().data("total", total).data("rows", records);
    }

    //多条件组合查询带分页
    @PostMapping("/pageTeacherCondition/{current}/{size}")
    public ResultVo pageListTeacherQuery(@PathVariable long current,
                                         @PathVariable int size,
                                         @RequestBody(required = false
                                         ) TeacherQuery teacherQuery) {
        Page<EduTeacher> pageTeacher = new Page<EduTeacher>(current, size);
        teacherService.pageQuery(pageTeacher, teacherQuery);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();

        return ResultVo.ok().data("total", total).data("rows", records);
    }

    //新增讲师
    @PostMapping("/save")
    public ResultVo save(@RequestBody EduTeacher eduTeacher) {
        boolean bool = teacherService.save(eduTeacher);
        if (bool) {
            return ResultVo.ok();
        } else {
            return ResultVo.error();
        }
    }

    //根据id查询
    @GetMapping("/getById/{id}")
    public ResultVo getById(@PathVariable String id) {
        EduTeacher teacher = teacherService.getById(id);
        return ResultVo.ok().data("item", teacher);
    }

    //修改讲师
    @PostMapping("/updateById")
    public ResultVo updateById(@RequestBody EduTeacher teacher) {
        boolean flag = teacherService.updateById(teacher);
        if (flag) {
            return ResultVo.ok();
        } else {
            return ResultVo.error();
        }
    }

}

