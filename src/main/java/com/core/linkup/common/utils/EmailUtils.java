package com.core.linkup.common.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailUtils {

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

    private MimeMessage createEmailForm(String to, String subject, String htmlContent) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        return message;
    }
}
