package com.teragrep.zep_01.regex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpliatblePrompt {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpliatblePrompt.class);

    private final String prompt;
    private final int newLineIndex;

    public SpliatblePrompt(String prompt) {
        this(prompt, prompt.indexOf('\n'));
    }

    public SpliatblePrompt(String prompt, int newlineIndex) {
        this.prompt = prompt;
        this.newLineIndex = newlineIndex;
    }

    public String regex() throws RegexInterpreterException {
        if (newLineIndex == -1) {
            throw new RegexInterpreterException("unrecognized prompt, please use regex on the first line and content on subsequent line(s)");
        }

        String regex = prompt.substring(0, newLineIndex);
        LOGGER.trace("Extracted regex <[{}]>", regex);
        return regex;
    }

    public String content() throws RegexInterpreterException {
        if (newLineIndex == -1) {
            throw new RegexInterpreterException("unrecognized prompt, please use regex on the first line and content on subsequent line(s)");
        }

        String content = prompt.substring(newLineIndex + 1);
        LOGGER.trace("Extracted content <[{}]>", content);
        return content;
    }


}
