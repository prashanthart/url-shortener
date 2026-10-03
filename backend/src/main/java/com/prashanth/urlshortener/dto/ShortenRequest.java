package com.prashanth.urlshortener.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class ShortenRequest {

    private String originalUrl;
    private String customAlias;
    private Instant expirationDate;
}
