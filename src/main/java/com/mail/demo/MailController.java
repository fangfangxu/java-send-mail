package com.mail.demo;


import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailController {

    public static void main(String[] args) throws Exception {
        String mail = "收件箱";
        StringBuilder content = new StringBuilder(100).append("发送正文内容");
        sendEmail(mail, "邮件主题", content.toString());
    }

    private static void sendEmail(String email, String subject, String content) throws Exception {
        JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
        //设置发送邮件的SMTP服务器地址
        senderImpl.setHost("发件服务器ip");
        senderImpl.setPort(587);
        //创建一个邮件对象
        MimeMessage mailMessage = senderImpl.createMimeMessage();
        //创建有邮件对象的服务类
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, "UTF-8");
        messageHelper.setTo(email);//设置发送给谁
        messageHelper.setFrom("发件人邮箱");
        messageHelper.setSubject(subject);//设置邮件标题
        messageHelper.setText(content, true);//设置邮件内容
        senderImpl.setUsername("发件箱用户名");
        senderImpl.setPassword("发件箱密码");
        Properties prop = new Properties();
        prop.put("mail.smtp.auth","true");//设置发送邮件需要身份认证
        prop.put("mail.smtp.timeout","25000");//设置发送超时时间
        prop.put("mail.smtp.starttls.enable","true");
        prop.put("mail.smtp.starttls.required","true");
        prop.setProperty("mail.smtp.socketFactory.port","587");
        prop.setProperty("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        prop.setProperty("mail.smtp.socketFactory.fallback","true");
        MailSSLSocketFactory sf=new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.socketFactory",sf);
        senderImpl.setJavaMailProperties(prop);
        senderImpl.send(mailMessage);


    }


}
