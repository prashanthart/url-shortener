package com.prashanth.urlshortener.modal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection="urls")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrlMapping {

    @Id
    private String Id;

    @Indexed(unique=true)
    private String shortCode;

    private String originalUrl;

    private Instant createdAt;

    @Indexed(expireAfter = "0s")
    private Instant expiresAt;

}
