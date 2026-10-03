package com.prashanth.urlshortener.util;

public final class Base62 {

    private static final String ALPHABET =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int BASE = ALPHABET.length(); // 62

    private Base62() {
    }

    public static String encode(long number){
        if(number<0){
            throw new IllegalArgumentException("number must not be negative");
        }
        if(number==0){
            return String.valueOf(ALPHABET.charAt(0));
        }
        StringBuilder sb = new StringBuilder();
        while(number>0){
            sb.append(ALPHABET.charAt((int) (number%BASE)));
            number /= BASE;
        }

        return sb.reverse().toString();

    }
}
