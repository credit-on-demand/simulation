package com.creditoonde.simulation.helper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class URLHelper {

    private URLHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static String decodeParam(String text) {
        try {
            return URLDecoder.decode(text, "UTF-8");
        } catch (IllegalArgumentException | UnsupportedEncodingException e) {
            return "";
        }
    }
}
