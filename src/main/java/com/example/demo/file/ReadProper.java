package com.example.demo.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zengxi.song
 * @date 2023/7/12
 */
public class ReadProper {

    public static void main(String[] args) {
        Map<String, String> map1 = readConfig("/Users/td/IdeaProjects/salaxy-doc/deployment/app_config/bifrost/application-production.properties");
        Map<String, String> map2 = readConfig("/Users/td/IdeaProjects/bifrost/web/src/main/resources/application-dev.properties");
//        Map<String, String> map2 = readConfig("/Users/td/IdeaProjects/tiangong-doc/docs/release/app_config/1.5.2-xc/bifrost/application.properties");

        System.out.println("第一份独有");
        for (String s : map1.keySet()) {
            if (!map2.containsKey(s)) {
                System.out.println(s);
            }
        }
        System.out.println("==============");
        System.out.println("第二份独有");
        for (String s : map2.keySet()) {
            if (!map1.containsKey(s)) {
                System.out.println(s);
            }
        }
    }

    public static Map<String, String> readConfig(String filePath) {
        Map<String, String> configMap = new LinkedHashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty() && !line.startsWith("#")) {
                    int separatorIndex = line.indexOf('=');
                    if (separatorIndex != -1) {
                        String key = line.substring(0, separatorIndex).trim();
                        String value = line.substring(separatorIndex + 1).trim();
                        configMap.put(key, value);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return configMap;
    }
}
