package com.teragrep.zep_01.regex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NamedGroupsPattern {

    private static final Logger LOGGER = LoggerFactory.getLogger(NamedGroupsPattern.class);
    private final Pattern pattern;

    public NamedGroupsPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public Map<Integer, String> namedGroupIndexes() throws RegexInterpreterException {
        final Method namedGroupsMethod;
        try {
            // java 11 does not have namedGroups as public so reflection is needed
            namedGroupsMethod = Pattern.class.getDeclaredMethod("namedGroups");
        }
        catch (NoSuchMethodException e) {
            LOGGER.error("reflection error getDeclaredMethod", e);
            throw new RegexInterpreterException("reflection error getDeclaredMethod", e);
        }

        namedGroupsMethod.setAccessible(true);

        final Map<String, Integer> groupMap;
        try {
            @SuppressWarnings("unchecked")
            final Map<String, Integer> groupMapLocal = (Map<String, Integer>) namedGroupsMethod.invoke(pattern);
            groupMap = groupMapLocal;
        }
        catch (InvocationTargetException | IllegalAccessException e) {
            LOGGER.error("reflection error invoke", e);
            throw new RegexInterpreterException("reflection error invoke", e);
        }

        // invert the map so group index points to name
        Map<Integer, String> indexToName = new HashMap<>();
        for (Map.Entry<String, Integer> entry : groupMap.entrySet()) {
            indexToName.put(entry.getValue(), entry.getKey());
        }

        return  indexToName;
    }

    Matcher matcher(final String content) {
        return pattern.matcher(content);
    }

    @Override
    public String toString() {
        return pattern.toString();
    }

}
