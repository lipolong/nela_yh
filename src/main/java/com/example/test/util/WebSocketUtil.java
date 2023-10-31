package com.example.test.util;

import org.java_websocket.client.WebSocketClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lp
 * @date 2022-04-15 14:11
 */
public class WebSocketUtil {

    private static Map<String, WebSocketClient> map = new ConcurrentHashMap<>(10);


    public static void addSocket(String name,WebSocketClient webSocketClient){
        WebSocketClient webSocket = getWebSocket(name);
        if(webSocket==null){
            map.put(name, webSocketClient);
        }else{
            System.out.println("已存在");
        }
    }

    public static WebSocketClient getWebSocket(String name){
        return map.get(name);
    }

    public static void removeWebSocket(String name){
        WebSocketClient webSocket = getWebSocket(name);
        if(webSocket!=null){
            webSocket.close();
            map.remove(name);
        }
    }

}
