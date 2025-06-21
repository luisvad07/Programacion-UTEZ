package utez.edu.mx.foodster.services.mailservice;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    public final Environment environment;
    private final JavaMailSender javaMailSender;

    public MailService(Environment environment, JavaMailSender javaMailSender) {
        this.environment = environment;
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(String mailAddress, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(environment.getProperty("spring.mail.username"));
        message.setTo(mailAddress);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }
    public void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);

        javaMailSender.send(message);
    }
}
