package com.hunter.eduservice.controller;

import com.hunter.commonUtils.ResultVo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduService/user")
@CrossOrigin
public class EduLoginController {
    //login
    @PostMapping("/login")
    public ResultVo login() {

        return ResultVo.ok().data("token", "admin");
    }


    //info
    @GetMapping("/info")
    public ResultVo getInfo() {

        return ResultVo.ok().data("roles", "[admin]")
                .data("name", "admin")
                .data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

}
