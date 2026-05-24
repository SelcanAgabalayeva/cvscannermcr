package com.selcan.notification_service.controller;

import com.selcan.notification_service.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notify")
@RequiredArgsConstructor
public class NotificationController {

    private final EmailService emailService;

    @PostMapping
    public String send(@RequestParam String email) {

        emailService.sendEmail(
                email,
                "CV Processed",
                "Your CV has been successfully processed!"
        );

        return "sent";
    }
}
