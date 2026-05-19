package com.teragrep.zep_01.regex;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonOutput2 {
    private final Pattern pattern;
    private final Map<String, Integer> groupMap;
    private final Matcher matcher;

    public JsonOutput2(final Pattern pattern, final Map<String, Integer> groupMap, final Matcher matcher) {
        this.pattern = pattern;
        this.groupMap = groupMap;
        this.matcher = matcher;
    }

    public JsonObject asJson() {
        final JsonObjectBuilder recordSchemaBuilder = Json.createObjectBuilder();

        recordSchemaBuilder.addNull("recordType");

        recordSchemaBuilder.add("regex", pattern.toString());

        final JsonArrayBuilder recordSchemeDataBuilder = Json.createArrayBuilder();

        // produce describable key value where describer is a decoration that can auto-analyze?
        for (String key : groupMap.keySet()) {
            final JsonObjectBuilder recordSchemaDatumBuilder = Json.createObjectBuilder();
            final String value = matcher.group(key);

            if (value == null) {
                recordSchemaDatumBuilder.addNull(key);
            }
            else {
                recordSchemaDatumBuilder.add(key, value);
            }
            recordSchemaDatumBuilder.addNull("columnDescription");
            recordSchemeDataBuilder.add(recordSchemaDatumBuilder.build());
        }

        recordSchemaBuilder.add("columns", recordSchemeDataBuilder.build());

        return recordSchemaBuilder.build();
    }

}
