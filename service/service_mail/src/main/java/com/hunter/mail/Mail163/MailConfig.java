package com.hunter.mail.Mail163;

import io.github.biezhi.ome.OhMyEmail;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailConfig implements InitializingBean {
    private String MY_EMAIL;
    private String SECRET_KEY;


    @Value("${myEmail.myEmail}")
    public void setMyEmail(String myEmail) {
        MY_EMAIL = myEmail;
    }

    @Value("${myEmail.secrectKey}")
    public void setSecretKey(String secretKey) {
        SECRET_KEY = secretKey;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(MY_EMAIL + ":" + SECRET_KEY);
        OhMyEmail.config(OhMyEmail.SMTP_163(false), MY_EMAIL, SECRET_KEY);
    }
}
