package com.example.test.ecdh;

import com.example.test.util.Base64;
import com.example.test.util.Base64Util;

import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author lp
 * @date 2022-04-29 14:22
 */
public class EDCHUtil {

    public static final String DH = "DH";
    /***
     * 测试001 生成密钥对
     * @return Alice的密钥对
     * @throws Exception
     */
    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(DH);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 根据客户端公钥生成服务器的密钥对
     * @param webPubKey web端公钥
     * @return 返回
     * @throws InvalidKeySpecException
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchAlgorithmException
     */
    public static KeyPair serverKey(byte[] webPubKey) throws InvalidKeySpecException, InvalidAlgorithmParameterException, NoSuchAlgorithmException {
        // 将Alice发过来的公钥数组解析成公钥对象
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(webPubKey);
        KeyFactory keyFactory = KeyFactory.getInstance(DH);
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        // 公钥生成自己的密钥对
        DHParameterSpec dhParameterSpec = ((DHPublicKey)publicKey).getParams();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(DH);
        keyPairGenerator.initialize(dhParameterSpec);
        return keyPairGenerator.generateKeyPair();
    }




    public static void main(String[] args) throws Exception {
        KeyPair keyPair = generateKeyPair();

        KeyPair ser = serverKey(keyPair.getPublic().getEncoded());
        System.out.println(Base64Util.encodeByte(ser.getPublic().getEncoded()));


//        String webPubKey = "BGcm7VHpFw5RMe2IQ7Vn16qYtnf1MZ/066P7OMy6h1iGBHQOgTSODVX7n/Ob617sb7ZZnE6CO0waTzou67wzs0o=";
//        KeyPair keyPair = serverKey(Base64Util.decodeString(webPubKey));
//        System.out.println(Base64Util.encodeByte(keyPair.getPublic().getEncoded()));

//        PrivateKey aPrivate = keyPair.getPrivate();
//        byte[] encoded1 = aPrivate.getEncoded();


    }


}
