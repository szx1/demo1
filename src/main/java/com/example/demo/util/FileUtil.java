package com.example.demo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zengxi.song
 * @date 2022/10/9
 */
public class FileUtil {

    public static String readFileAsString(InputStream in, String encoding) {

        StringBuilder buf = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {

            InputStreamReader read = new InputStreamReader(in, encoding);
            bufferedReader = new BufferedReader(read);
            String lineTxt;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                buf.append(lineTxt).append("\n");
            }
            read.close();

        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return buf.toString();
    }

}
