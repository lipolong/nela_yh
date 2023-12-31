package com.example.test.ecdh;

import com.example.test.util.Base64Util;
import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.ECGenParameterSpec;
import java.util.Arrays;
import java.util.Map;

/**
 * @author lp
 * @date 2022-04-28 16:07
 * 测试
 */
public class DHTest {

    //甲方公钥
    private static byte[] publicKey1;
    //甲方私钥
    private static byte[] privateKey1;
    //甲方本地密钥
    private static byte[] key1;
    //乙方公钥
    private static byte[] publicKey2;
    //乙方私钥
    private static byte[] privateKey2;
    //乙方本地密钥
    private static byte[] key2;

    /**
     * 前端生成的公钥
     */
    public static final String WEB_PUB_KEY="BN4w+xBMYfJoQZ7iYBFYNv2nZBWfQAQUKnbBvJ7v9j1uCtCPhk1QVlmbf9JOkQ7uXM68uJlb4Ff+u7ODLtHYBB8=";



    public static void main(String[] args) throws Exception {




//        initKey();
//        System.out.println();
//        //生成本地密钥
//        key1 = DHCoder.getSecretKey(Base64Util.decodeString(WEB_PUB_KEY), privateKey1);
//        System.out.println("共享密钥密钥:" + Base64.encodeBase64String(key1));

        initKey();
        System.out.println();
        System.out.println("===甲方向乙方发送加密数据===");
        String input1 = "求知若饥，虚心若愚。";
        System.out.println("原文:\n" + input1);
        System.out.println("---使用甲方本地密钥对数据进行加密---");
        //使用甲方本地密钥对数据加密
        byte[] encode1 = DHCoder.encrypt(input1.getBytes(), key1);
        System.out.println("加密:\n" + Base64Util.encodeByte(encode1));
        System.out.println("---使用乙方本地密钥对数据库进行解密---");
        //使用乙方本地密钥对数据进行解密
        byte[] decode1 = DHCoder.decrypt(encode1, key2);
        String output1 = new String(decode1);
        System.out.println("解密:\n" + output1);

        System.out.println("/~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~..~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~/");
        initKey();
        System.out.println("===乙方向甲方发送加密数据===");
        String input2 = "好好学习，天天向上。";
        System.out.println("原文:\n" + input2);
        System.out.println("---使用乙方本地密钥对数据进行加密---");
        //使用乙方本地密钥对数据进行加密
        byte[] encode2 = DHCoder.encrypt(input2.getBytes(), key2);
        System.out.println("加密:\n" + Base64Util.encodeByte(encode2));
        System.out.println("---使用甲方本地密钥对数据进行解密---");
        //使用甲方本地密钥对数据进行解密
        byte[] decode2 = DHCoder.decrypt(encode2, key1);
        String output2 = new String(decode2);
        System.out.println("解密:\n" + output2);
    }




    /**
     * 初始化密钥
     *
     * @throws Exception
     */
    public static void initKey() throws Exception {
        //生成甲方密钥对
        Map<String, Object> keyMap1 = DHCoder.initKey();
        publicKey1 = DHCoder.getPublicKey(keyMap1);
        privateKey1 = DHCoder.getPrivateKey(keyMap1);
        System.out.println("甲方公钥:\n" + Base64.encodeBase64String(publicKey1));
        System.out.println("甲方私钥:\n" + Base64.encodeBase64String(privateKey1));
        //由甲方公钥产生本地密钥对
        Map<String, Object> keyMap2 = DHCoder.initKey(publicKey1);
        publicKey2 = DHCoder.getPublicKey(keyMap2);
        privateKey2 = DHCoder.getPrivateKey(keyMap2);
        System.out.println("乙方公钥:\n" + Base64.encodeBase64String(publicKey2));
        System.out.println("乙方私钥:\n" + Base64.encodeBase64String(privateKey2));
        //使用乙方公钥，甲方私钥 共同生成甲方本地密钥
        key1 = DHCoder.getSecretKey(publicKey2, privateKey1);
        System.out.println("甲方本地密钥:\n" + Base64.encodeBase64String(key1));
        //使用甲方公钥，乙方私钥 共同生成乙方本地密钥
        key2 = DHCoder.getSecretKey(publicKey1, privateKey2);
        System.out.println("乙方本地密钥:\n" + Base64.encodeBase64String(key2));
    }

}
