package com.engineerLee.rolloverapi.registration.repository;

import com.engineerLee.rolloverapi.registration.models.WhatsApp;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WhatsAppRepository extends MongoRepository<WhatsApp, Long> {
}
