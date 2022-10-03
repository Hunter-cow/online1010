package com.hunter.mail.service.Impl;

import com.hunter.commonUtils.RandomCode;
import com.hunter.mail.Mail163.sendMail;
import com.hunter.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public int sendMailCode(String email) {
        //获取验证码
        String str = "谷粒学院验证码是%s,五分钟内有效！";
        String code = RandomCode.getSixBitRandom();
        String format = String.format(str, code);
        sendMail Mail = new sendMail(email, format);
        int send = Mail.send();
        if (send != -1) {
            redisTemplate.opsForValue().set(email, code, 5, TimeUnit.MINUTES);
        }
        return send;
    }
}
