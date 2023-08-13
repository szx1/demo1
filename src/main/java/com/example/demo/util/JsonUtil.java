package com.example.demo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

/**
 * @author zengxi.song
 * @date 2023/5/26
 */
public class JsonUtil {

    private static final ObjectMapper mapper = new ObjectMapper();


    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private JsonUtil() {
    }


    /**
     * json序列化——jackson
     *
     * @param obj
     * @return
     * @throws JsonProcessingException
     */
    @SneakyThrows
    public static String jsonSerialize(Object obj) {
        return jsonSerialize(obj, true);
    }

    @SneakyThrows
    public static String jsonSerialize(Object obj, boolean pretty) {
        if (obj == null) {
            return null;
        }
        if (pretty) {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        }
        return mapper.writeValueAsString(obj);
    }

}
