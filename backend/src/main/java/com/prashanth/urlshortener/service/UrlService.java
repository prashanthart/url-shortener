package com.prashanth.urlshortener.service;

import com.prashanth.urlshortener.dto.ShortenRequest;
import com.prashanth.urlshortener.dto.ShortenResponse;
import com.prashanth.urlshortener.exception.AliasAlreadyExistsException;
import com.prashanth.urlshortener.exception.UrlExpiredException;
import com.prashanth.urlshortener.exception.UrlNotFoundException;
import com.prashanth.urlshortener.modal.UrlMapping;
import com.prashanth.urlshortener.repository.UrlMappingRepository;
import com.prashanth.urlshortener.util.Base62;
import com.prashanth.urlshortener.validation.UrlValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlService {

    private final UrlValidator validator;
    private final UrlMappingRepository repository;
    private final CounterService counterService;

    private static final long SPACE = 3_521_614_606_208L;   // 62^7 = all possible 7-character codes
    private static final long MULTIPLIER = 2_654_435_761L;  // odd and not divisible by 31

//    @Value("${app.base-url}")
    @Value("${app.base-url}")
    private String baseUrl;

    public ShortenResponse createShortUrl(ShortenRequest request){

        String originalUrl = validator.validateAndNormalize(request.getOriginalUrl());
        Instant now = Instant.now();
        Instant expiresAt = request.getExpirationDate()!=null ? request.getExpirationDate() : now.plus(365, ChronoUnit.DAYS);
        String alias = request.getCustomAlias();
        UrlMapping saved = (alias!=null && !alias.isBlank())
                         ? saveWithAlias(alias.trim(),originalUrl,now,expiresAt)
                         : saveWithGeneratedCode(originalUrl,now,expiresAt);

        return new ShortenResponse(
                baseUrl + "/"+ saved.getShortCode(),saved.getShortCode(),saved.getExpiresAt());

    }

    private UrlMapping saveWithGeneratedCode(String originalUrl, Instant now, Instant expiresAt) {
        for(int i=0;i<5;i++){
            long next = counterService.getNextSequence("url_counter");
            String code = generateCode(next);
            try{
                log.info("Saving in DB.");
                return repository.save(build(code,originalUrl,now,expiresAt));
            }
            catch (Exception e){

            }
        }

        throw new IllegalStateException("Could not generate a unique short code, please try again");
    }

    private String generateCode(long next) {
        // BigInteger avoids long overflow when multiplying
        long scrambled = BigInteger.valueOf(next)
                .multiply(BigInteger.valueOf(MULTIPLIER))
                .mod(BigInteger.valueOf(SPACE))
                .longValue();

        // always 7 characters: pad the front with '0'
        return String.format("%7s", Base62.encode(scrambled)).replace(' ', '0');
    }
//    public UrlMapping saveWithGeneratedCode()

    public UrlMapping saveWithAlias(String alias, String originalUrl, Instant now, Instant expiresAt){
        validator.validateAlias(alias);

        if(repository.existsByShortCode(alias)){
            throw new AliasAlreadyExistsException(alias);
        }

        try{
            return repository.save(build(alias,originalUrl,now,expiresAt));
        }
        catch (Exception e){
            throw new AliasAlreadyExistsException(alias);
        }

    }

    public UrlMapping build(String code, String originalUrl, Instant now, Instant expiresAt){

        return UrlMapping.builder()
                .shortCode(code)
                .originalUrl(originalUrl)
                .createdAt(now)
                .expiresAt(expiresAt)
                .build();
    }

    public String resolve(String shortCode){
        UrlMapping mapping = repository.findByShortCode(shortCode)
                .orElseThrow(()-> new UrlNotFoundException(shortCode));

        if(mapping.getExpiresAt()!=null && mapping.getExpiresAt().isBefore(Instant.now())){
            throw new UrlExpiredException(shortCode);
        }
        log.info("url:{}",mapping.getOriginalUrl());

        return mapping.getOriginalUrl();

    }






}
