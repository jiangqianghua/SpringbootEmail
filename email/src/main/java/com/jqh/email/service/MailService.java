package com.jqh.email.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender ;

    @Value("${spring.mail.username}")
    private String from ;
    public void sendSimpleMail(String to , String subject, String content){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        message.setFrom(from);
        mailSender.send(message);
    }

    /**
     * 发送网页邮箱
     * @param to
     * @param subject
     * @param content
     * @throws Exception
     */
    public void sendHtmlMail(String to , String subject, String content) throws Exception{
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content,true);
        message.setFrom(from);
        mailSender.send(message);
    }

    public void sendAttachmentsMail(String to , String subject , String content,
                                    String filePath) throws Exception{
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content);

        FileSystemResource file = new FileSystemResource(new File(filePath));
        String fileName = file.getFilename();
        helper.addAttachment(fileName,file);
        mailSender.send(message);
    }

    public void sendInlinResourceMail(String to ,String subject , String content,
                                      String srcPath,String srcId) throws Exception{
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content,true);
        FileSystemResource resource = new FileSystemResource(new File(srcPath));
        helper.addInline(srcId,resource);
        mailSender.send(message);
    }
}
