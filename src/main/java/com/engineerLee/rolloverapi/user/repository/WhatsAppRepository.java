package com.engineerLee.rolloverapi.user.repository;

import com.engineerLee.rolloverapi.user.models.WhatsApp;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WhatsAppRepository extends MongoRepository<WhatsApp, Long> {
}
