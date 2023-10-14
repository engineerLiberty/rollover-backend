package com.engineerLee.rolloverapi.registration.controller;

import com.engineerLee.rolloverapi.registration.models.WhatsApp;
import com.engineerLee.rolloverapi.registration.service.WhatsAppLinkService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class WhatAppController {
    private final WhatsAppLinkService whatsAppLinkService;
    @PostMapping("register")
    @Tag(name = "Add WhatsApp Link")
    @ResponseStatus(HttpStatus.CREATED)
    public WhatsApp addNewWhatsAppLink(String whatsAppLink) {
        return whatsAppLinkService.addWhatsAppLink(whatsAppLink);
    }
}
