package com.example.test.controller;

import net.sf.json.JSONObject;
import org.spongycastle.util.encoders.Hex;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.SignatureException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lp
 * @date 2022-05-05 10:23
 * 测试请求参数加密
 */
@RestController
public class SignController {



//    @RequestMapping("/testSign")
//    public Object testSign(String params, String sign) {
//        System.out.println("收到的参数信息:" + params);
//        System.out.println("收到的参数sign:" + sign);
//        net.sf.json.JSONObject object = net.sf.json.JSONObject.fromObject(params);
//        if (checkSign(sign, object)) {
//            return "测试成功";
//        }
//        return "userAddress与明文不匹配";
//    }
//
//
//    public static boolean checkSign(String sign, net.sf.json.JSONObject params) {
//        String userAddress = params.getString("userAddress");
//        System.out.println("userAddress:" + userAddress);
//        String s = Numeric.toHexString(params.toString().getBytes());
//        byte[] bytes = Numeric.hexStringToByteArray(s);
//        byte[] ethereumMessageHash = Sign.getEthereumMessageHash(bytes);
//        String substring = sign.substring(2);
//        Sign.SignatureData signatureData = new Sign.SignatureData(Hex.decode(substring.substring(128, 130)), Hex.decode(substring.substring(0, 64)), Hex.decode(substring.substring(64, 128)));
//        BigInteger pubKey;
//        try {
//            pubKey = Sign.signedMessageHashToKey(ethereumMessageHash, signatureData);
//            String address = "0x" + Keys.getAddress(pubKey);
//            String checksumAddress = Keys.toChecksumAddress(address);
//            System.out.println("解析后获取地址:" + checksumAddress);
//            return address.equals(checksumAddress);
//        } catch (SignatureException e) {
//            e.printStackTrace();
//            System.out.println("发成异常");
//            return false;
//        }
//    }
//
//    private static final String SIGN="0x2d7e616e0801627e50fe7f94a8816f0586563c04023edf53c8ed1f734de1e409559deb1cb58d1fa73e44fde450594313c75d114533d47312373b56f766c18dba1b";
//
//    public static void testCheckSign(){
//        String str="{'userAddress':'0x687b5a626340effe324118f172092344dd7fcb49'}";
//        String s = Numeric.toHexString(str.getBytes());
//        byte[] bytes = Numeric.hexStringToByteArray(s);
//        byte[] ethereumMessageHash = Sign.getEthereumMessageHash(bytes);
//        String substring = SIGN.substring(2);
//        Sign.SignatureData signatureData = new Sign.SignatureData(Hex.decode(substring.substring(128, 130)), Hex.decode(substring.substring(0, 64)), Hex.decode(substring.substring(64, 128)));
//        BigInteger pubKey;
//        try {
//            pubKey = Sign.signedMessageHashToKey(ethereumMessageHash, signatureData);
//            String address = "0x" + Keys.getAddress(pubKey);
//            String checksumAddress = Keys.toChecksumAddress(address);
//            System.out.println("解析后获取地址:" + address);
//        } catch (SignatureException e) {
//            e.printStackTrace();
//            System.out.println("发成异常");
//        }
//
//    }




    public static byte[] hexToByteArray(String inHex) {
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1) {
            // 奇数
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {
            // 偶数
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = hexToByte(inHex.substring(i, i + 2));
            j++;
        }
        return result;
    }

    public static byte hexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }

    public static void main(String[] args) throws Exception {
//        String address = Keys.toChecksumAddress("0xfb6E73934092793BdBa717745E73B05D27E095b4");
//        String address1 = Keys.toChecksumAddress("0xfb6e73934092793bdba717745e73b05d27e095b4");
//        System.out.println(address);
//        System.out.println(address1);

//        testCheckSign();


//        Map<String,Object> map = new HashMap<>();
//        map.put("userAddress","0x687b5a626340effe324118f172092344dd7fcb49");
//        JSONObject object = JSONObject.fromObject(map.toString());
//        System.out.println(object.toString());
//        //1.字符串转成Hash
//        String s = Numeric.toHexString(object.toString().getBytes());
////        //2.hash转成byte数组
//        byte[] messageHash = Numeric.hexStringToByteArray(s);
//        String privateKey = "cce0ad67ff4bcf83aac66e16e2bba4ec8fb0eeebe40fae338ac79b4b4e19c774";
//        Credentials credentials = Credentials.create(privateKey);
//        Sign.SignatureData signatureData = Sign.signPrefixedMessage(messageHash, credentials.getEcKeyPair());
//        String sign = "0x" + Hex.toHexString(signatureData.getR()) + Hex.toHexString(signatureData.getS()) + Hex.toHexString(signatureData.getV());
//        System.out.println(sign);
////        String signMessage="0x834b69e90999bcc3c8b5e1d9fa5b9da60368673ed286a696222b3c490d8635907c5a30844a67599b6d93b5015c6dd2171daa513fafad331da1fe1deced3a3baa1b";
////        String signMessage="0x077dbdadc8fdd459bc9cbcd031504840a139a5f228cc587afdcb5e2b4ee6208d34fc6ed1e8c7248fb25c049f19a595725635096bff0d900b182430b2c06a59151c";
//        byte[] ethereumMessageHash = Sign.getEthereumMessageHash(messageHash);
//        String substring = sign.substring(2);
////        System.out.println(substring);
////        System.out.println("获取R:"+substring.substring(0,64));
////        System.out.println("获取R长度:"+substring.substring(0,64).length());
////        System.out.println("获取S:"+substring.substring(64,128));
////        System.out.println("获取S长度:"+substring.substring(64,128).length());
////        System.out.println("获取V:"+substring.substring(128,130));
////        System.out.println("获取V长度:"+substring.substring(128,130).length());
////        //V R S 顺序
//        Sign.SignatureData signatureData1 = new Sign.SignatureData(Hex.decode(substring.substring(128,130)),Hex.decode(substring.substring(0,64)),Hex.decode(substring.substring(64,128)));
////        Sign.SignatureData signatureData1 = new Sign.SignatureData(signatureData.getV(),signatureData.getR(),signatureData.getS());
//        BigInteger pubKey = Sign.signedMessageHashToKey(ethereumMessageHash, signatureData1);
//        String address = Keys.getAddress(pubKey);
//        System.out.println("address:"+"0x" + address);


    }


//    public static byte[] toByteArray(char[] array) {
//        return toByteArray(array, Charset.defaultCharset());
//    }
//
//    public static byte[] toByteArray(char[] array, Charset charset) {
//        CharBuffer cbuf = CharBuffer.wrap(array);
//        ByteBuffer bbuf = charset.encode(cbuf);
//        return bbuf.array();
//    }
//
//
//    public static byte[] stringToByteArray(String s) {
//        byte[] byteArray = new byte[s.length() / 2];
//        String[] strBytes = new String[s.length() / 2];
//        int k = 0;
//        for (int i = 0; i < s.length(); i = i + 2) {
//            int j = i + 2;
//            strBytes[k] = s.substring(i, j);
//            byteArray[k] = (byte) Integer.parseInt(strBytes[k], 16);
//            k++;
//        }
//        return byteArray;
//    }


}
