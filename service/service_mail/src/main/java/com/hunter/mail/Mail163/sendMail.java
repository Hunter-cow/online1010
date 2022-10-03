
package com.hunter.mail.Mail163;

import com.hunter.commonUtils.RandomCode;
import com.hunter.commonUtils.ResultVo;
import io.github.biezhi.ome.OhMyEmail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class sendMail {
    private String TO_EMAIL;
    private String SEND_MSG;


    public int send() {
        try {
            OhMyEmail.subject("这是一封测试TEXT邮件")
                    .from("Test邮箱")
                    .to(TO_EMAIL)
                    .text(SEND_MSG)
                    .send();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

}
