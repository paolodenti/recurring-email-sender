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
    @Scheduled(fixedDelayString = "#{appProperties.getMail().getDelayMilliseconds()}")
    public void sendEmail() {

        log.info("Sending all emails ...");
        for (String to : appProperties.getMail().getTo()) {
            sendEmail(to);
        }
        log.info("All emails sent.");
    }

    /**
     * Send email.
     *
     * @param to to
     */
    public void sendEmail(String to) {

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

            log.debug("Sending email to {}", to);
            emailSender.send(mimeMessage);
            log.debug("Email to {} sent", to);
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("Cannot send email", e);
        }
    }
}
