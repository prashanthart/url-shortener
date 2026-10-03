package com.prashanth.urlshortener.exception;

public class UrlExpiredException extends RuntimeException {
    public UrlExpiredException(String shortCode) {
        super("The link \"" + shortCode + "\" has expired");
    }
}
