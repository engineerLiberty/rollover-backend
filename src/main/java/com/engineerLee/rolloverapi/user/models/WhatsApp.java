package com.engineerLee.rolloverapi.user.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "whatsApp")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WhatsApp {
    @org.springframework.data.annotation.Id
    private Long Id;
}
