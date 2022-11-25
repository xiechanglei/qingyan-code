package com.xiechanglei.code.qingyan.code.serialize.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonSerializer {
    private static final ObjectMapper om = new ObjectMapper();

    static {
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    public static String toJsonString(Object object) throws JsonProcessingException {
      return om.writeValueAsString(object);
    }

    public static <T> T toObject(String json, Class<T> clazz) throws JsonProcessingException {
      return om.readValue(json, clazz);
    }

}
