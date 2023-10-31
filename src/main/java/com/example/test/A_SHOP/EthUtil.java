package com.example.test.A_SHOP;


import okhttp3.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

/**
 * @author: lp
 * @create: 2023-08-29 21:55
 * 一元购--无节点(以太坊相关)
 **/
public class EthUtil {


    /**
     * 以太坊 测试 API秘钥
     */
    public static final String ERC20_API_KEY = "EU21WDMSRGJ74PI6DXWXZGDBVKB6XHZ9WQ";
    /**
     * 以太坊测试地址
     */
    public static final String ERC20_ACCOUNT_ADDRESS = "0xD9e3bA5e70cC4e0738FA277426a31119c3B9E95C";

    /**
     * 目前支持的三个网络的Host为： api.etherscan.io
     * * api-ropsten.etherscan.io
     * * api-kovan.etherscan.io
     * * api-rinkeby.etherscan.io
     * 0-999999999
     */
//    public static final String ERC20_HOST_URL="https://api.etherscan.io/api?module=account&action=tokentx&address={}&startblock=0&endblock=999999999&sort=desc&apikey={}";
    public static final String ERC20_HOST_URL = "http://api.etherscan.io/api?module=account&action=tokentx";

    public static void main(String[] args) throws IOException {
        //根据账号+APIKey查询
        String url = getURL(ERC20_ACCOUNT_ADDRESS, "0", "999999999", "desc");
        System.out.println(url);
        System.out.println("返回信息:");
        EthUtil ethUtil = new EthUtil();
        String s = ethUtil.sendGet(url);
        //TODO 解析获取数据
        System.out.println(s);
    }




    private CloseableHttpClient httpClient;
    private HttpGet httpGet;
    public static final String CONTENT_TYPE = "Content-Type";
    /**
     * 发送get请求
     * @param url 发送链接 拼接参数  http://localhost:8090/order?a=1
     * @return
     * @throws IOException
     */
    public String sendGet(String url) throws IOException {
        httpClient = HttpClients.createDefault();
        httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        String resp;
        try {
            HttpEntity entity = response.getEntity();
            resp = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }
        return resp;
    }


//    OkHttpClient client = new OkHttpClient().newBuilder()
//            .build();
//    MediaType mediaType = MediaType.parse("text/plain");
//    RequestBody body = RequestBody.create(mediaType, "");



    public static String getURL(String address, String startBlock, String endBlock, String sort) {
        return ERC20_HOST_URL + "&address=" + address +
                "&startblock=" + startBlock +
                "&endblock=" + endBlock +
                "&sort=" + sort +
                "&apikey=" + ERC20_API_KEY;
    }


}
