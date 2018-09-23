package com.jqh.email;

import com.jqh.email.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailApplicationTests {

	@Resource
	MailService mailService ;

	@Resource
	TemplateEngine templateEngine ;
	@Test
	public void contextLoads() {
	}

	@Test
	public void sendSimpleMailTest(){
		mailService.sendSimpleMail("240437339@qq.com","这是第一封邮件","这是邮件内容");
	}

	@Test
	public void sendHtmlMail() throws Exception{

		mailService.sendHtmlMail("240437339@qq.com","这是第一封邮件","<h1>这是邮件内容</h1>");
	}

	@Test
	public void sendAttachmentsMail() throws Exception{

		String filePath = "E:\\江强华简历_new.doc";
		mailService.sendAttachmentsMail("240437339@qq.com","这是带附件的邮件","<h1>这是带附件的邮件</h1>",filePath);
	}

	@Test
	public void sendInlineMail() throws Exception{

		String filePath = "E:\\IMG_20151018_211231.jpg";
		String  content = "<html><body>图片:<img src=\'cid:001\'/></body></html>";
		mailService.sendInlinResourceMail("240437339@qq.com","这是带图片的邮件",content,filePath,"001");
	}

	@Test
	public void sendTemplateMail()throws Exception{
		Context context = new Context();
		context.setVariable("id","001");
		String emailContent = templateEngine.process("emailTemplate",context);
		mailService.sendHtmlMail("240437339@qq.com","这是第一封模板邮件",emailContent);
	}

}
