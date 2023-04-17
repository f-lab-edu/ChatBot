package com.flab.fire_inform.global.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class MailSendManager {

    @Value("${spring.mail.username}")
    private String fromAddress;

    private final JavaMailSender javaMailSender;

    public MailSendManager(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(String toAddress, String title, String content) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, "UTF-8");
        messageHelper.setFrom(fromAddress);
        messageHelper.setTo(toAddress);
        messageHelper.setSubject(title);
        messageHelper.setText(content);
        javaMailSender.send(message);
    }
}
