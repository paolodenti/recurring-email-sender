package com.github.paolodenti.sender.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailSenderService {

    @Scheduled(fixedDelayString = "${app.mail.delay}")
    public void sendEmail() {
        log.info("Sending email...");
    }
}
