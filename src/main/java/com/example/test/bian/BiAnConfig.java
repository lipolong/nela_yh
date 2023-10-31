package com.example.test.bian;

import cn.hutool.core.thread.ThreadUtil;
import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.connector.client.impl.WebsocketClientImpl;

import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;

/**
 * @author lp
 * @date 2022-08-29 15:57
 */
public class BiAnConfig {

    private BiAnConfig() {
    }

    private static ExecutorService service =ThreadUtil.newExecutor(10);
//https://api.it120.cc/gooking/forex/rate?fromCode=CNY&toCode=USD   获取人民币与美元汇率

    public static void main(String[] args) {
        service.submit(()->{
            WebsocketClientImpl client = new WebsocketClientImpl();
            client.klineStream("btcusdt", "1s", (message)-> System.out.println("【USDT】:"+ message));
        });
//        service.submit(()->{
//            WebsocketClientImpl client = new WebsocketClientImpl();
//            client.klineStream("btcbusd", "1s", (message)-> System.out.println("【BUSD】:"+ message));
//        });
//        service.submit(()->{
//            WebsocketClientImpl client = new WebsocketClientImpl();
//            client.klineStream("bnbbtc", "1s", (message)-> System.out.println("【BNB】:"+ message));
//        });
//        service.submit(()->{
//            WebsocketClientImpl client = new WebsocketClientImpl();
//            client.klineStream("adabtc", "1s", (message)-> System.out.println("【ADA】:"+ message));
//        });
//        service.submit(()->{
//            WebsocketClientImpl client = new WebsocketClientImpl();
//            client.klineStream("dotbtc", "1s", (message)-> System.out.println("【DOT】:"+ message));
//        });
//        service.submit(()->{
//            WebsocketClientImpl client = new WebsocketClientImpl();
//            client.klineStream("solbtc", "1s", (message)-> System.out.println("【SOL】:"+ message));
//        });
//        service.submit(()->{
//            WebsocketClientImpl client = new WebsocketClientImpl();
//            client.klineStream("maticbtc", "1s", (message)-> System.out.println("【MATIC】:"+ message));
//        });
//
//        service.submit(()->{
//            WebsocketClientImpl client = new WebsocketClientImpl();
//            client.klineStream("dogebtc", "1s", (message)-> System.out.println("【DOGE】:"+ message));
//        });
//
//        service.submit(()->{
//            WebsocketClientImpl client = new WebsocketClientImpl();
//            client.klineStream("trxbtc", "1s", (message)-> System.out.println("【TRX】:"+ message));
//        });
//
//        service.submit(()->{
//            WebsocketClientImpl client = new WebsocketClientImpl();
//            client.klineStream("ltcbtc", "1s", (message)-> System.out.println("【LTC】:"+ message));
//        });
//
//
//
    }


}
