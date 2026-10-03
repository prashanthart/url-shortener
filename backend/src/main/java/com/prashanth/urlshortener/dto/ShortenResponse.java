package com.prashanth.urlshortener.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class ShortenResponse {
    public String shortUrl;
    public String shortCode;
    public Instant expiresAt;
}
