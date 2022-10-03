package com.hunter.ucenter.controller;


import com.hunter.commonUtils.JwtUtils;
import com.hunter.commonUtils.ResultVo;
import com.hunter.commonUtils.UcenterMemberVo;
import com.hunter.ucenter.entity.Member;
import com.hunter.ucenter.entity.Vo.LoginVo;
import com.hunter.ucenter.entity.Vo.RegisterVo;
import com.hunter.ucenter.service.MemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author Hunter
 * @since 2022-09-22
 */
@RestController
@RequestMapping("/eduUcenter/member")
@CrossOrigin
public class MemberController {

    @Autowired
    private MemberService memberService;

    //登录
    @PostMapping("/login")
    public ResultVo login(@RequestBody LoginVo loginVo) {
        String token = memberService.login(loginVo);
        return ResultVo.ok().data("token", token);
    }

    //注册
    @PostMapping("/register")
    public ResultVo register(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return ResultVo.ok();
    }

    @GetMapping("/getUserInfoForJwt")
    public ResultVo getUserInfoForJwt(HttpServletRequest request) {
        String id = JwtUtils.getMemberIdByJwtToken(request);
        Member member = memberService.getById(id);
        return ResultVo.ok().data("userInfo", member);
    }

    @PostMapping("/getUserInfoById/{id}")
    public UcenterMemberVo getUserInfoById(@PathVariable String id) {
        Member member = memberService.getById(id);
        UcenterMemberVo ucenterMemberVo = new UcenterMemberVo();
        BeanUtils.copyProperties(member, ucenterMemberVo);
        return ucenterMemberVo;
    }


}

