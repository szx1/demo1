package com.example.demo.util;

import org.apache.commons.collections4.MapUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zengxi.song
 * @date 2023/5/26
 */
public class HttpUtils {

    private HttpUtils() {
    }

    /**
     * 传参封装为具体的实体类
     *
     * @param url
     * @param params
     * @return
     */
    public static String doPostObject(String url, Object params) {
        return doPost(url, JsonUtil.jsonSerialize(params));
    }

    /**
     * 传参封装为Map
     *
     * @param url
     * @param params
     * @return
     */
    public static String doPostMap(String url, Map<String, Object> params) {
        return doPost(url, JsonUtil.jsonSerialize(params));
    }

    public static String doPost(String url, String params) {
//        CurlUtils.curlPost(url, params)
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        String result = null;

        // 创建httpPost远程连接实例
        HttpPost httpPost = new HttpPost(url);
        // 配置请求参数实例
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 设置连接主机服务超时时间
                .setConnectionRequestTimeout(35000)// 设置连接请求超时时间
                .setSocketTimeout(60000)// 设置读取数据连接超时时间
                .build();
        // 为httpPost实例设置配置
        httpPost.setConfig(requestConfig);
        // 设置请求头
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.setEntity(new StringEntity(params, StandardCharsets.UTF_8));
        try {
            // httpClient对象执行post请求,并返回响应参数对象
            httpResponse = httpClient.execute(httpPost);
            // 从响应对象中获取响应内容
            HttpEntity entity = httpResponse.getEntity();
            result = EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != httpResponse) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String doGet(String url, Map<String, Object> params) throws URISyntaxException, IOException {
//        CurlUtils.curlGet(url, params)
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse;
        String result = null;
        URI uri;
        try {
            if (MapUtils.isNotEmpty(params)) {
                List<NameValuePair> pairs = new ArrayList<>();
                params.forEach((k, v) -> {
                    NameValuePair pair = new BasicNameValuePair(k, String.valueOf(v));
                    pairs.add(pair);
                });
                uri = new URIBuilder(url).setParameters(pairs).build();
            } else {
                uri = new URIBuilder(url).build();
            }
            HttpGet httpGet = new HttpGet(uri);
            httpResponse = httpClient.execute(httpGet);
            // 从响应对象中获取响应内容
            HttpEntity entity = httpResponse.getEntity();
            result = EntityUtils.toString(entity);
        } finally {
            // 关闭资源
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
