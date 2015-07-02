package cz.jiripinkas.jba.service;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import cz.jiripinkas.jba.entity.User;
import freemarker.template.Configuration;
import freemarker.template.Template;

@Service("templateSendEmail")
public class TemplateSendEmail {
	private static JavaMailSender sender = null;
	private static Configuration freemarkerConfiguration = null;

	public void setFreemarkerConfiguration(Configuration freemarkerConfiguration) {
		this.freemarkerConfiguration = freemarkerConfiguration;
	}

	public void setSender(JavaMailSender sender) {
		this.sender = sender;
	}

	private String getMailText(User user) {
		String htmlText = "";
		try {
			Template tpl = freemarkerConfiguration
					.getTemplate("registerUser.ftl");
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", user.getName());
			map.put("id", user.getConfirmId());
			htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(tpl,
					map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return htmlText;
	}

	public boolean sendTemplateMail(User user) throws MessagingException {
		MimeMessage msg = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, false, "utf8");
		helper.setFrom("www.system-exe.com.vn");
		helper.setTo(user.getEmail());
		helper.setSubject("test sending email");
		String htmlText = getMailText(user);
		helper.setText(htmlText, true);
		sender.send(msg);
		return true;
	}

}
