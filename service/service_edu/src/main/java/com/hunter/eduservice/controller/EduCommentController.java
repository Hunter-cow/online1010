package com.hunter.eduservice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hunter.commonUtils.JwtUtils;
import com.hunter.commonUtils.ResultVo;
import com.hunter.commonUtils.UcenterMemberVo;
import com.hunter.eduservice.client.UcenterClient;
import com.hunter.eduservice.entity.EduComment;
import com.hunter.servicebase.exceptionHandler.ConsumeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.hunter.eduService.service.EduCommentService;


import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author Hunter
 * @since 2022-09-26
 */
@RestController
@RequestMapping("/eduService/eduComment")
@CrossOrigin
public class EduCommentController {

    @Autowired
    private EduCommentService commentService;

    @Autowired
    private UcenterClient ucenterClient;

    //分页查询评论
    @GetMapping("/pageComment/{page}/{limit}")
    public ResultVo pageComment(@PathVariable long page, @PathVariable long limit, @RequestParam("courseId") String courseId) {
        Page<EduComment> page1 = new Page<>(page, limit);
        Map<String, Object> map = commentService.getPageComment(page1, courseId);
        return ResultVo.ok().data(map);
    }

    @PostMapping("/auth/save")
    public ResultVo auth(HttpServletRequest request, @RequestBody EduComment eduComment) {

        String userId = JwtUtils.getMemberIdByJwtToken(request);//会员id
        if (StringUtils.isEmpty(userId)) {
            throw new ConsumeException(20001, "请先进行登录");
        }
        eduComment.setMemberId(userId);
        //查询会员信息
        UcenterMemberVo ucenterMemberVo = ucenterClient.getUserInfoById(userId);
        eduComment.setAvatar(ucenterMemberVo.getAvatar());
        eduComment.setNickname(ucenterMemberVo.getNickname());
        commentService.save(eduComment);
        return ResultVo.ok();
    }
}

