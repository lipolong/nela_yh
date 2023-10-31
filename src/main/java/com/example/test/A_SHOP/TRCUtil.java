package com.example.test.A_SHOP;

import java.io.IOException;

/**
 * @author: lp
 * @create: 2023-08-29 22:42
 * 一元购--无节点(波场相关)
 **/
public class TRCUtil {

    /**
     * 波场地址
     */
    public static final String TRC_ADDRESS = "TPV7rsEB3gKVJ97S8e3z6McUqPpzNSuQTZ";
    /**
     * 波场API-KEY
     */
//    public static final String TRC_KEY = "79de0b0c-3674-454f-a604-48a3a18826c2";
    public static final String TRC_KEY = "38721cec-b3da-4db9-8d8d-f681429de15d";

    /**
     * 波场主网地址
     */
    public static final String TRC_URL = "https://api.trongrid.io";
    /**
     * 按账户地址获取交易信息
     */
    public static final String TRC_REQ_URL = "https://api.shasta.trongrid.io/v1/accounts/%s/transactions?limit=200";

    public static void main(String[] args) throws IOException {
        String url = String.format(TRC_REQ_URL,TRC_ADDRESS);
        System.out.println(url);
        EthUtil ethUtil = new EthUtil();
        String s = ethUtil.sendGet(url);
        System.out.println(s);
    }






//    OkHttpClient client = new OkHttpClient();
//
//    Request request = new Request.Builder()
//            .url("https://api.shasta.trongrid.io/v1/accounts/TPV7rsEB3gKVJ97S8e3z6McUqPpzNSuQTZ/transactions?limit=200")
//            .get()
//            .addHeader("accept", "application/json")
//            .build();
//
//    Response response = client.newCall(request).execute();
}
