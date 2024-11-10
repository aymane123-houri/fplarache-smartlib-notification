package fplarache.smartlib.notifications.service;

import fplarache.smartlib.notifications.model.Messages;
import fplarache.smartlib.notifications.notification.ConfirmationNotification;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendConfirmationNotificationEmail(ConfirmationNotification notification) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(notification.getEmprunt().getUser().getEmail());
            helper.setSubject("Confirmation de l'emprunt de votre livre");
            helper.setText(new Messages().getConfirmationMessage(notification.getEmprunt()));
            mailSender.send(message);
        } catch (MessagingException e) {
            e.getCause();
        }
    }

}