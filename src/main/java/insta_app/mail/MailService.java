package insta_app.mail;

import java.util.Date;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import insta_app.utility.MessageModel;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class MailService {


	private JavaMailSender mailSender;

	public MailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendMailMessage(MessageModel model) throws MessagingException
	{
				MimeMessage message = mailSender.createMimeMessage();
				
				MimeMessageHelper helper = new MimeMessageHelper(message);
				
				helper.setTo(model.getTo());
				helper.setSubject(model.getSubject());
				helper.setSentDate(new Date());
				helper.setText(model.getText(),true);
				mailSender.send(message);
				
				
				
	}
}
