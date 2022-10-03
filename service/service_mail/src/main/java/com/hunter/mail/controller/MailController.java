package com.hunter.mail.controller;

import com.hunter.commonUtils.ResultVo;
import com.hunter.mail.Mail163.sendMail;
import com.hunter.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/eduMail/sendMail")
@CrossOrigin
public class MailController {

    @Autowired
    private MailService mailService;


    @GetMapping("/{email}")
    public ResultVo sendMails(@PathVariable("email") String email) {
        System.out.println(email);
        int send = mailService.sendMailCode(email);
        if (send != -1) {
            return ResultVo.ok().message("发送邮件成功");
        } else {
            return ResultVo.error().message("发送邮件失败");
        }
    }

}
