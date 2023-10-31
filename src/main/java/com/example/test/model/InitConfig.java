package com.example.test.model;

import com.example.test.util.Base64Util;
import io.edfs.tskandroidv2.TSKJni;
import org.bouncycastle.util.encoders.Hex;

/**
 * @author lp
 * @date 2022-01-13 17:05
 */
public class InitConfig {

    static {
        String filePrivate = "MG4DAgeAAgEgAiBlty0Lmcp3/QzGJ3T4B+Fx4QME6VXbvsLkEZB5HWLe0gIhAIqFncpsOZ1iEF/T8PRn6Q1+Rt1jjB9DyD3xhZW197d5AiBaRT7YwWRIsm0GsXM896TkikpKxx0tA4rlkxjQNrzoHA==";
        String tskid = "2aEb94Ba0A0D67f29007d770Cf1742cFc21c4E89";
        byte[] decode = Hex.decode(tskid);
        //初始化
        int init = TSKJni.Init();
        System.out.println("初始化TSK返回:"+init);
        //登录
        int i = TSKJni.LoginUser(decode);
        System.out.println("登录TSK返回:"+i);
        //设置权限需要2次操作
        byte[] bytes;
        try {
            bytes = Base64Util.decodeString(filePrivate);
            int setAdd = TSKJni.SetHoldIdentity(3, decode, (short) -1, -1, bytes, bytes.length);
            System.out.println("权限:增加匹配项:"+setAdd);
            int setDefault = TSKJni.SetHoldIdentity(1, decode, (short) -1, -1, bytes, bytes.length);
            System.out.println("权限:默认匹配项:"+setDefault);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
