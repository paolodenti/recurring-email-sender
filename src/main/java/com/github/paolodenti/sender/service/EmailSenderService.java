package com.github.paolodenti.sender.service;

import com.github.paolodenti.sender.config.AppProperties;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailSenderService {

    private final AppProperties appProperties;
    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;

    /**
     * Scheduled task to send email.
     */
    @Scheduled(fixedDelayString = "${app.mail.delay}")
    public void sendEmail() {

        log.info("Sending email...");
        for (String to : appProperties.getMail().getTo()) {
            sendEmail(to);
        }
    }

    /**
     * Send email.
     *
     * @param to to
     */
    public void sendEmail(String to) {

        log.debug("sending email");
        if (to == null) {
            log.debug("No recipient, skipping");
            return;
        }

        try {
            final MimeMessage mimeMessage = emailSender.createMimeMessage();
            final MimeMessageHelper email = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            email.addTo(to);

            String subject = appProperties.getMail().getTitle();
            email.setSubject(subject);

            email.setFrom(new InternetAddress(appProperties.getMail().getFrom(), appProperties.getMail().getPlainName()));

            final Context ctx = new Context(Locale.ENGLISH);
            ctx.setVariable("title", appProperties.getMail().getTitle());
            ctx.setVariable("body", appProperties.getMail().getBody());

            final String htmlContent = this.templateEngine.process("emailtemplate", ctx);
            email.setText(htmlContent, true);

            log.debug("Sending email");
            emailSender.send(mimeMessage);
            log.debug("Email sent");
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("Cannot send email", e);
        }
    }
}
