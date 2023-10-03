package com.alfish.arealmvp.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
@EnableAutoConfiguration
public class MailService {

	private static final Logger LOG = LogManager.getLogger(MailService.class);

	private final JavaMailSenderImpl mailSender;
	private final ResourceLoader resourceLoader;
	private final String resetTemplate;
	private final String registerTemplate;
	private static final String PASSWORD_PLACEHOLDER = "<!--PASSWORD HERE-->";
	private static final String RESET_KEY_PLACEHOLDER = "<!--RESET KEY HERE-->";

	@Autowired
	public MailService(JavaMailSenderImpl mailSender, ResourceLoader resourceLoader) throws IOException {
		this.mailSender = mailSender;
		this.resourceLoader = resourceLoader;
		this.resetTemplate = loadHtmlFile("/templates/pswd-reset.html");
		this.registerTemplate = loadHtmlFile("/templates/register-confirm.html");
	}

	public void sendRegisterConfirmation(String to, String resetKey) {
		String subject = "New Account - " + to;
		StringBuilder sb = new StringBuilder(registerTemplate);
		sb.insert(sb.indexOf(RESET_KEY_PLACEHOLDER), resetKey);
		sendEmail(mailSender.getUsername(), to, subject, sb.toString());
	}

	public void sendPasswordReset(String to, String tempPswd, String resetKey) {
		String subject = "Password reset for " + to;
		StringBuilder sb = new StringBuilder(resetTemplate);
		sb.insert(sb.indexOf(PASSWORD_PLACEHOLDER), tempPswd);
		sb.insert(sb.indexOf(RESET_KEY_PLACEHOLDER), resetKey);
		sendEmail(mailSender.getUsername(), to, subject, sb.toString());
	}

	private void sendEmail(String from, String to, String subject, String body) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, true);
			mailSender.send(message);
		}
		catch (MessagingException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	private String loadHtmlFile(String uri) throws IOException {
		Resource resource = resourceLoader.getResource("classpath:" + uri);
		byte[] htmlBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
		return new String(htmlBytes, StandardCharsets.UTF_8);
	}

}
