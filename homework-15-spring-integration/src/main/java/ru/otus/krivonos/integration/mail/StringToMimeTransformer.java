package ru.otus.krivonos.integration.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Created by LuckyMan on 30.10.2016.
 */
@Service
public class StringToMimeTransformer {
    @Autowired
    private JavaMailSender mailSender;

    public MimeMessage mailCreation(String messageText, @Header("target") String target, @Header("subject") String subject) {

        MimeMessage mimeMessage;
        try {
            mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.toString());
            message.setTo(target);
            message.setSubject(subject);
            message.setText(messageText, false);
            message.setSentDate(new Date(System.currentTimeMillis()));
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return mimeMessage;
    }
}
