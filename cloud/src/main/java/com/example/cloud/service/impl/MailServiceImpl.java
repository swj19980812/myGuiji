package com.example.cloud.service.impl;

import com.example.cloud.dao.LoginDao;
import com.example.cloud.service.MailService;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.util.List;
import java.util.Random;

@Service("mailService")
public class MailServiceImpl implements MailService {

    @Resource
    LoginDao loginDao;

    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public String SendMail(String userName) {
        String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
        SimpleMailMessage messagel = new SimpleMailMessage();
        String eMail = loginDao.getEMail(userName);
        System.out.println(eMail);
        messagel.setFrom(from);
        messagel.setTo(eMail);
        messagel.setSubject("【云存储】邮箱验证");
        messagel.setText("亲爱的用户 " + userName + " ：您好！\n" +
                "\n" +
                "    您收到这封这封电子邮件是因为您 (也可能是某人冒充您的名义) 申请了修改邮箱。假如这不是您本人所申请, 请不用理会这封电子邮件, 但是如果您持续收到这类的信件骚扰, 请您尽快联络管理员。\n" +
                "\n" +
                "    请使用以下验证码完成后续修改密码流程\n" +
                "    \n    " +
                checkCode + "\n" +
                "   \n" +
                "    注意:请您在收到邮件10分钟内使用，否则该验证码将会失效。" +
                "    \n" +
                "    我们将一如既往、热忱的为您服务！\n");
        mailSender.send(messagel);
        loginDao.update_code_request(userName,checkCode);
        loginDao.update_time(userName);
        return "true";
    }

}

