package com.core.linkup.member.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, String text) throws MessagingException {
        MimeMessage mimeMessage = createEmailForm(to, subject, text);
        try{
            javaMailSender.send(mimeMessage);
            log.info("Email sent to " + to);
        } catch (RuntimeException e){
            log.debug("Email sent to {} on subject {}: {}"
                    + " exception occurred", to, subject, text);
        }
    }

    private MimeMessage createEmailForm(String to, String subject, String text) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.setFrom(new InternetAddress("jeanssang@naver.com"));
        message.addRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject(subject);
        message.setText(text);

        return message;
    }
}
