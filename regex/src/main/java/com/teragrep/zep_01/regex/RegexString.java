package com.teragrep.zep_01.regex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RegexString {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegexString.class);
    private final String regexString;

    public RegexString(String regexString) {
        this.regexString = regexString;
    }

    public Pattern asPattern() throws RegexInterpreterException {
        final Pattern pattern;
        try {
            pattern = Pattern.compile(regexString);
        }
        catch (PatternSyntaxException e) {
            LOGGER.error("regex compilation failed", e);
            throw new RegexInterpreterException("regex compilation failed", e);
        }
        return pattern;
    }
}
