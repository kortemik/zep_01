package com.teragrep.zep_01.regex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkipablePrompt {

    private static final Logger LOGGER = LoggerFactory.getLogger(SkipablePrompt.class);

    private final String prompt;
    private final int newlineIndex;

    public SkipablePrompt(String prompt) {
        this(prompt, prompt.indexOf('\n'));
    }

    public SkipablePrompt(String prompt, int newLineIndex) {
        this.prompt = prompt;
        this.newlineIndex = newLineIndex;
    }

    public String skipFirstLine() throws RegexInterpreterException {
        LOGGER.trace("Interpreting prompt <[{}]>", prompt);

        if (newlineIndex == -1) {
            throw new RegexInterpreterException("unrecognized prompt, please newline after interpreter declaration and use regex on the first line and content on subsequent line(s)");
        }
        String omitted = prompt.substring(0, newlineIndex);
        LOGGER.trace("omitting <[{}]>",  omitted);

        return prompt.substring(newlineIndex + 1);
    }
}
