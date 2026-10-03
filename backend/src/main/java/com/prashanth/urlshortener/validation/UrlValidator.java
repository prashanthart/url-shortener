package com.prashanth.urlshortener.validation;

import com.prashanth.urlshortener.exception.InvalidRequestException;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.Set;

@Component
public class UrlValidator {

    private static final int MAX_URL_LENGTH = 2048;

    private static final Set<String> RESERVED_ALIASES =
            Set.of("api", "admin", "shorten", "error", "health", "actuator", "static");


    public String validateAndNormalize(String raw){
        if(raw==null || raw.isBlank()){
            throw new InvalidRequestException("originalUrl must not be empty");
        }
        String url = raw.trim();

        if(url.length()>MAX_URL_LENGTH){
            throw new InvalidRequestException("originalUrl is too long");
        }
        URI uri;
        try{
            uri = new URI(url);
        }
        catch (URISyntaxException e){
            throw new InvalidRequestException("originalUrl is not valid url");
        }

        String schema = uri.getScheme();
        if(schema==null ||
                !(schema.equalsIgnoreCase("http") || schema.equalsIgnoreCase("https") )){
            throw new InvalidRequestException("Only http and https URLs are allowed");
        }

        if(uri.getHost()==null || uri.getHost().isBlank()){
            throw new InvalidRequestException("originalUrl must contain host");
        }

        return url;

    }

    public void validateAlias(String alias){

        if(RESERVED_ALIASES.contains(alias.toLowerCase(Locale.ROOT))){
            throw new InvalidRequestException("This alias \""+alias+"\" is reserved, choose another one");

        }

    }

}
