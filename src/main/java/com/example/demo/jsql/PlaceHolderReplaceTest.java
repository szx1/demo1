package com.example.demo.jsql;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author admin
 * @since 2023/8/13
 */
public class PlaceHolderReplaceTest {


    private static final Pattern pattern = Pattern.compile("\\$\\{.*?\\}");

    public static void main(String[] args) {
        String originalString = "这是一个${}和${占位符2}的示例。";

        Matcher matcher = pattern.matcher(originalString);

        StringBuffer modifiedString = new StringBuffer();
        while (matcher.find()) {
            String placeholder = matcher.group(); // 获取匹配到的占位符
            String replacement = getReplacement(placeholder); // 根据占位符获取替换文本
            matcher.appendReplacement(modifiedString, replacement);
        }
        matcher.appendTail(modifiedString);

        System.out.println(modifiedString.toString());

        String test1 = pattern.matcher(originalString).replaceAll("test1");
        System.out.println(test1);
        String test = originalString.replaceAll("\\$\\{[^}]*}", "test");
        System.out.println(test);
    }

    private static String getReplacement(String placeholder) {
        // 在这里根据占位符内容返回相应的替换文本
        // 例如：根据占位符"${占位符1}"返回"替换文本1"
        return "";
    }
}
