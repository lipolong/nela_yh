package com.example.test.api.util;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author: lp
 * @create: 2023-06-04 15:53
 * 对外API工具类
 **/
public class ExChangeUtils {

    public static void main(String[] args) {
        Map<String ,Object> map = new HashMap<>(16);
        map.put("a","test");
        map.put("z","hehe");
        map.put("c","ceshi");
        map.put("d","nihao");
        //加密
        String sign = getSign(map, "123456");
        System.out.println(sign);
        boolean b = checkSign(map, "123456", sign);
        System.out.println("验证签名:"+b);
    }

    /**
     * 校验sign
     * @param map 请求参数 排除sign
     * @param secret 秘钥
     * @return 返回 true 验签正确 false 验签失败
     */
    public static boolean checkSign(Map<String,Object> map,String secret,String sign){
        map.remove("sign");
        String urlParams = getUrlParams(map)+secret;
        //将参数+秘钥 md5一下
        String s = Md5Util.md5Encode(urlParams, "UTF-8");
        return s.equals(sign);
    }


    /**
     * 签名
     * @param map 请求参数
     * @param secret 秘钥
     * @return 返回前面
     */
    public static String getSign(Map<String,Object> map,String secret){
        String urlParams = getUrlParams(map)+secret;
        return Md5Util.md5Encode(urlParams,"UTF-8");
    }

    /**
     * 将map转换成url参数模式  根据a-z形式排序:a=test&c=ceshi&d=nihao&z=hehe
     *
     * @param map 请求参数
     * @return 返回
     */
    public static String getUrlParams(Map<String, Object> map) {
        if (map == null) {
            return "";
        }
        TreeMap<String,Object> treeMap = new TreeMap<>(map);
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : treeMap.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = substringBeforeLast(s);
        }
        return s;
    }

    private static String substringBeforeLast(String str) {
        if (!StringUtils.isEmpty(str) && !StringUtils.isEmpty("&")) {
            int pos = str.lastIndexOf("&");
            return pos == -1 ? str : str.substring(0, pos);
        } else {
            return str;
        }
    }
}
