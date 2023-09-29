package com.engineerLee.rolloverapi.registration.service;

import com.engineerLee.rolloverapi.registration.domain.WhatsApp;
import com.engineerLee.rolloverapi.registration.repository.WhatsAppRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WhatsAppLinkService {
    private final WhatsAppRepository linkRepository;

    public WhatsApp addWhatsAppLink(String link) {
        WhatsApp whatsappLink = WhatsApp.builder()
                .whatsappGroupLink(link)
                .build();
        return linkRepository.save(whatsappLink);
    }
}
