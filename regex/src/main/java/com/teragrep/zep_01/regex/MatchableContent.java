package com.teragrep.zep_01.regex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Matcher;

public class MatchableContent {

    private static final Logger LOGGER = LoggerFactory.getLogger(MatchableContent.class);
    private final NamedGroupsPattern namedGroupsPattern;
    private final String content;

    public MatchableContent(NamedGroupsPattern namedGroupsPattern, final String content) {
        this.namedGroupsPattern = namedGroupsPattern;
        this.content = content;
    }

    public List<Map<String, String>> captureGroups() throws RegexInterpreterException {
        Matcher matcher = namedGroupsPattern.matcher(content);

        if (!matcher.matches()) {
            LOGGER.warn("regex does not match content");
            throw new RegexInterpreterException("Provided regex\n----\n" + namedGroupsPattern + "\n----\nDoes not match provided content\n----\n" + content + "\n----");
        }

        Map<Integer, String> indexToName = namedGroupsPattern.namedGroupIndexes();

        List<Map<String, String>> allMatches = new ArrayList<>();
        while (matcher.find()) {

            Map<String, String> matchMap = new LinkedHashMap<>();

            for (int i = 1; i <= matcher.groupCount(); i++) {
                String value = matcher.group(i);
                String name = indexToName.getOrDefault(i, "group_" + i);
                matchMap.put(name, value);
            }

            allMatches.add(matchMap);
        }

        return allMatches;
    }
}
