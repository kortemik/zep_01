package com.teragrep.zep_01.regex;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonWriter;
import jakarta.json.JsonWriterFactory;
import jakarta.json.stream.JsonGenerator;

import java.io.StringWriter;
import java.util.Collections;
import java.util.Map;

public class PrettyJsonStringImpl {

    private final JsonWriterFactory writerFactory;

    public PrettyJsonStringImpl() {
        this(Collections.singletonMap(JsonGenerator.PRETTY_PRINTING, true));
    }

    public PrettyJsonStringImpl(Map<String, Boolean> writerFactoryConfig) {
        this(Json.createWriterFactory(writerFactoryConfig));
    }

    public PrettyJsonStringImpl(JsonWriterFactory writerFactory) {
        this.writerFactory = writerFactory;
    }

    public String pretty(JsonObject jsonObject) {
        final StringWriter stringWriter = new StringWriter();
        try (JsonWriter jsonWriter = writerFactory.createWriter(stringWriter)) {
            jsonWriter.writeObject(jsonObject);
        }
        return stringWriter.toString();
    }
}
