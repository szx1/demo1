package com.example.demo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * @author zengxi.song
 * @date 2023/5/26
 */
@Slf4j
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

    public static <T> T jsonDeserialize(String content, TypeReference<T> typeReference) {
        if (StringUtils.isBlank(content)) {
            return null;
        }
        try {
            return mapper.readValue(content, typeReference);
        } catch (IOException e) {
            log.error("Deserialization exception", e);
        }
        return null;
    }

    public static <T> T jsonDeserialize(String content, Class<T> clazzType) {
        if (StringUtils.isBlank(content)) {
            return null;
        }
        try {
            return mapper.readValue(content, clazzType);
        } catch (IOException e) {
            log.error("Deserialization exception!", e);
        }
        return null;
    }

}
