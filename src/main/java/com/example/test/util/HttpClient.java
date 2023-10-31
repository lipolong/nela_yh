package com.example.test.util;

import net.sf.json.JSONObject;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

/**
 * @author: lp
 * @create: 2023-06-12 04:14
 **/
public class HttpClient {
    private static ConnectionSocketFactory plainsf;
    private static LayeredConnectionSocketFactory sslsf;
    private static Registry<ConnectionSocketFactory> registry;
    private static PoolingHttpClientConnectionManager cm;

    private static HttpRequestRetryHandler httpRequestRetryHandler;

    private static RequestConfig requestConfig;

    private static CloseableHttpClient httpClient;

    static {
        plainsf = PlainConnectionSocketFactory.getSocketFactory();
        sslsf = SSLConnectionSocketFactory.getSocketFactory();
        registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", plainsf)
                .register("https", sslsf)
                .build();
        cm = new PoolingHttpClientConnectionManager(registry);
        // 最大连接数
        cm.setMaxTotal(30);
        // 每个路由基础的连接
        cm.setDefaultMaxPerRoute(10);
        //请求重试处理
        httpRequestRetryHandler = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount >= 5) {// 如果已经重试了5次，就放弃
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                    return false;
                }
                if (exception instanceof InterruptedIOException) {// 超时
                    return false;
                }
                if (exception instanceof UnknownHostException) {// 目标服务器不可达
                    return false;
                }
                if (exception instanceof SSLException) {// ssl握手异常
                    return false;
                }
                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                // 如果请求是幂等的，就再次尝试
                return !(request instanceof HttpEntityEnclosingRequest);
            }
        };

        requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(5000)
                .setConnectTimeout(5000)
                .setSocketTimeout(5000)
                .build();

        httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .setRetryHandler(httpRequestRetryHandler)
                .build();

    }

    public static JSONObject doJsonPost(String requestUrl, String param) {
        String result;
        HttpPost httpPost = new HttpPost(requestUrl);
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        try {
            httpPost.setEntity(new StringEntity(param, "UTF-8"));
            org.apache.http.client.HttpClient httpClient = HttpClientBuilder.create().build();
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
            return JSONObject.fromObject(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpPost.releaseConnection();
        }
        return null;
    }

    public static String doGet(String url) {
        String result = null;
        CloseableHttpResponse response = null;
        HttpGet httpget = new HttpGet(url);
        try {
            String resultEnc = "UTF-8";
            httpget.setConfig(requestConfig);
            response = httpClient.execute(httpget, HttpClientContext.create());
            result = EntityUtils.toString(response.getEntity(), resultEnc);
        } catch (Exception e) {
            return result;
        } finally {
            if (null != response) {
                try {
                    response.close();
                } catch (IOException ignored) {
                }
            }
            httpget.abort();
        }
        return result;
    }
}
