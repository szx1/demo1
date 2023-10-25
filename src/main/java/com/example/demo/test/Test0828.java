package com.example.demo.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zengxi.song
 * @date 2023/8/28
 */
public class Test0828 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        String s = in.nextLine();
        List<String> list = Arrays.stream(s.split(",")).sorted((str1, str2) -> {
            return checkStr(str1, str2);
        }).collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        list.forEach(sb::append);
        System.out.println(
                sb
        );
    }

    private static int checkStr(String str1, String str2) {
        String tmp1 = str1 + str2;
        String tmp2 = str2 + str1;
        if (tmp1.length() < tmp2.length()) {
            return 1;
        }
        if (tmp1.length() > tmp2.length()) {
            return -1;
        }

        for (int i = 0; i < tmp1.length(); i++) {
            if (tmp1.charAt(i) < tmp2.charAt(i)) {
                return 1;
            }
        }
        return 0;
    }
}
