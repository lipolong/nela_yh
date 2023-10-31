package com.example.test.ecdh;

import com.example.test.util.Base64Util;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jcajce.provider.symmetric.AES;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import sun.nio.cs.ext.GBK;

import javax.crypto.KeyAgreement;
import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;

public class LyhTest {
    public static final String STD_NAME = "secp256k1";


    // 读取2个字节转为无符号整型
    public static int shortbyte2int(byte[] res) {
        DataInputStream dataInputStream = new DataInputStream(
                new ByteArrayInputStream(res));
        int a = 0;
        try {
            a = dataInputStream.readUnsignedShort();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return a;
    }


    public static void main(String[] args) throws Exception{
        Security.addProvider(new BouncyCastleProvider());
        Provider[] providers = Security.getProviders();
        for (Provider provider : providers) {
            System.out.println(provider);
        }
        String webPub="BGcm7VHpFw5RMe2IQ7Vn16qYtnf1MZ/066P7OMy6h1iGBHQOgTSODVX7n/Ob617sb7ZZnE6CO0waTzou67wzs0o=";
        byte[] webDecode = Base64Util.decodeString(webPub);

        KeyFactory kf = KeyFactory.getInstance("EC");
        PublicKey remoteAlicePub = kf.generatePublic(new X509EncodedKeySpec(webDecode));
//        KeyPairGenerator aliceKeyGen = KeyPairGenerator.getInstance("ECDH", "BC");
////        aliceKeyGen.initialize(new ECGenParameterSpec(STD_NAME), new SecureRandom());
//        aliceKeyGen.initialize(new ECGenParameterSpec(STD_NAME));
//        KeyPair alicePair = aliceKeyGen.generateKeyPair();
//        ECPublicKey alicePub = (ECPublicKey)alicePair.getPublic();
//        ECPrivateKey alicePvt = (ECPrivateKey)alicePair.getPrivate();
//        byte[] alicePubEncoded = alicePub.getEncoded();
//        byte[] alicePvtEncoded = alicePvt.getEncoded();
//        System.out.println("Bob public: " + DatatypeConverter.printHexBinary(alicePubEncoded));
//        System.out.println("Bob private: " + DatatypeConverter.printHexBinary(alicePvtEncoded));
//        KeyAgreement bobKeyAgree = KeyAgreement.getInstance("ECDH");
//        bobKeyAgree.init(alicePvt);
//        bobKeyAgree.doPhase(remoteAlicePub, true);
//        System.out.println("Bob secret: " + DatatypeConverter.printHexBinary(bobKeyAgree.generateSecret()));


//        KeyPairGenerator aliceKeyGen = KeyPairGenerator.getInstance("ECDH", "BC");
////        aliceKeyGen.initialize(new ECGenParameterSpec(STD_NAME), new SecureRandom());
//        aliceKeyGen.initialize(new ECGenParameterSpec(STD_NAME));
//        KeyPair alicePair = aliceKeyGen.generateKeyPair();
//        ECPublicKey alicePub = (ECPublicKey)alicePair.getPublic();
//        ECPrivateKey alicePvt = (ECPrivateKey)alicePair.getPrivate();
//        byte[] alicePubEncoded = alicePub.getEncoded();
//        byte[] alicePvtEncoded = alicePvt.getEncoded();
//        String encoded = Base64.getEncoder().encodeToString(alicePubEncoded);
//        System.out.println("Alice public base64: " + encoded);
//        System.out.println("Alice public: " + DatatypeConverter.printHexBinary(alicePubEncoded));
//        System.out.println("Alice private: " + DatatypeConverter.printHexBinary(alicePvtEncoded));
//        KeyFactory kf = KeyFactory.getInstance("EC");
//        PublicKey remoteAlicePub = kf.generatePublic(new X509EncodedKeySpec(alicePubEncoded));
//        KeyPairGenerator bobKeyGen = KeyPairGenerator.getInstance("ECDH", "BC");
//        bobKeyGen.initialize(new ECGenParameterSpec(STD_NAME), new SecureRandom());
//        KeyPair bobPair = bobKeyGen.generateKeyPair();
//        ECPublicKey bobPub = (ECPublicKey)bobPair.getPublic();
//        ECPrivateKey bobPvt = (ECPrivateKey)bobPair.getPrivate();
//        byte[] bobPubEncoded = bobPub.getEncoded();
//        byte[] bobPvtEncoded = bobPvt.getEncoded();
//        System.out.println("Bob public: " + DatatypeConverter.printHexBinary(bobPubEncoded));
//        System.out.println("Bob private: " + DatatypeConverter.printHexBinary(bobPvtEncoded));
//        KeyAgreement bobKeyAgree = KeyAgreement.getInstance("ECDH");
//        bobKeyAgree.init(bobPvt);
//        bobKeyAgree.doPhase(remoteAlicePub, true);
//        System.out.println("Bob secret: " + DatatypeConverter.printHexBinary(bobKeyAgree.generateSecret()));
//        // RESPOND hex(bobPubEncoded)
//        // Alice derives secret
//        KeyFactory aliceKf = KeyFactory.getInstance("EC");
//        PublicKey remoteBobPub = aliceKf.generatePublic(new X509EncodedKeySpec(bobPubEncoded));
//        KeyAgreement aliceKeyAgree = KeyAgreement.getInstance("ECDH");
//        aliceKeyAgree.init(alicePvt);
//        aliceKeyAgree.doPhase(remoteBobPub, true);
//        System.out.println("Alice secret: " + DatatypeConverter.printHexBinary(aliceKeyAgree.generateSecret()));














//        ECParameterSpec paramSpec = ECNamedCurveTable.getParameterSpec("secp256k1");
////        Provider BC = new BouncyCastleProvider();
//        KeyAgreement agr = KeyAgreement.getInstance("ECDH");

        //X509EncodedKeySpec 和 PKCS8KeySpec 为EncodedKeySpec的子类，前者用于转换公钥编码密钥，后者用于转换私钥编码密钥
//        KeyFactory aliceKf = KeyFactory.getInstance("EC");
//        PublicKey remoteBobPub = aliceKf.generatePublic(new X509EncodedKeySpec(bobPubEncoded));
//        KeyAgreement aliceKeyAgree = KeyAgreement.getInstance("ECDH");
//        aliceKeyAgree.init(keyPair.getPrivate());
//        aliceKeyAgree.doPhase(remoteBobPub, true);
//        System.out.println("Alice secret: " + DatatypeConverter.printHexBinary(aliceKeyAgree.generateSecret()));





//        agr.init(keyPair.getPrivate());
//        agr.doPhase(keyPair.getPublic(), true);
//        KeyFactory aliceKf = KeyFactory.getInstance("EC");






//        byte[] secret = agr.generateSecret();
//        System.out.println("Alice secret: " + DatatypeConverter.printHexBinary(agr.generateSecret()));


//        String s = Arrays.toString(keyPair.getPublic().getEncoded());


//        System.out.println(DatatypeConverter.printHexBinary(keyPair.getPublic().getEncoded()));
//        KeyFactory aliceKf = KeyFactory.getInstance("EC");
//        PublicKey remoteBobPub = aliceKf.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(webKey)));
//        KeyAgreement aliceKeyAgree = KeyAgreement.getInstance("ECDH");
//        aliceKeyAgree.init(keyPair.getPrivate());
//        aliceKeyAgree.doPhase(remoteBobPub, true);
//        System.out.println("Alice secret: " + DatatypeConverter.printHexBinary(aliceKeyAgree.generateSecret()));
//





//        String pubKeyEncoded = Base64.getEncoder().encodeToString(pubKey);
//        System.out.println(pubKeyEncoded);
//        String priKeyEncoded = Base64.getEncoder().encodeToString(priKey);
//        System.out.println(priKeyEncoded);
//        KeyFactory aliceKf = KeyFactory.getInstance("EC");
//        PublicKey remoteBobPub = aliceKf.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(webKey)));
//        KeyAgreement aliceKeyAgree = KeyAgreement.getInstance("ECDH");
//        aliceKeyAgree.init(keyPair.getPrivate());
//        aliceKeyAgree.doPhase(remoteBobPub, true);
//        System.out.println("Alice secret: " + DatatypeConverter.printHexBinary(aliceKeyAgree.generateSecret()));

        //57896044618658097711785492504343953926418782139537452191302581570759080747168
        //115792089237316195423570985008687907852837564279074904382605163141518161494337

        //81466056097164636523853858232018837369879952074851312703891640462599441436685
        //105928117863787827727692614246361058111614529227425245541178292702513962598761



//        Security.addProvider(new BouncyCastleProvider());
// Alice sets up the exchange
//        Provider[] providers = Security.getProviders();
//        for (Provider provider : providers) {
//            System.out.println(provider);
//        }
//        KeyPairGenerator aliceKeyGen = KeyPairGenerator.getInstance("ECDH", "BC");
////        aliceKeyGen.initialize(new ECGenParameterSpec(STD_NAME), new SecureRandom());
//        aliceKeyGen.initialize(new ECGenParameterSpec(STD_NAME));
//        KeyPair alicePair = aliceKeyGen.generateKeyPair();
//        ECPublicKey alicePub = (ECPublicKey)alicePair.getPublic();
//        ECPrivateKey alicePvt = (ECPrivateKey)alicePair.getPrivate();
//        byte[] alicePubEncoded = alicePub.getEncoded();
//        byte[] alicePvtEncoded = alicePvt.getEncoded();
//        String encoded = Base64.getEncoder().encodeToString(alicePubEncoded);
//        System.out.println("Alice public base64: " + encoded);
//        System.out.println("Alice public: " + DatatypeConverter.printHexBinary(alicePubEncoded));
//        System.out.println("Alice private: " + DatatypeConverter.printHexBinary(alicePvtEncoded));





        // POST hex(alicePubEncoded)
        // Bob receives Alice's public key
//        KeyFactory kf = KeyFactory.getInstance("EC");
//        PublicKey remoteAlicePub = kf.generatePublic(new X509EncodedKeySpec(alicePubEncoded));
//        KeyPairGenerator bobKeyGen = KeyPairGenerator.getInstance("ECDH", "BC");
//        bobKeyGen.initialize(new ECGenParameterSpec(STD_NAME), new SecureRandom());
//        KeyPair bobPair = bobKeyGen.generateKeyPair();
//        ECPublicKey bobPub = (ECPublicKey)bobPair.getPublic();
//        ECPrivateKey bobPvt = (ECPrivateKey)bobPair.getPrivate();
//        byte[] bobPubEncoded = bobPub.getEncoded();
//        byte[] bobPvtEncoded = bobPvt.getEncoded();
//        System.out.println("Bob public: " + DatatypeConverter.printHexBinary(bobPubEncoded));
//        System.out.println("Bob private: " + DatatypeConverter.printHexBinary(bobPvtEncoded));
//        KeyAgreement bobKeyAgree = KeyAgreement.getInstance("ECDH");
//        bobKeyAgree.init(bobPvt);
//        bobKeyAgree.doPhase(remoteAlicePub, true);
//        System.out.println("Bob secret: " + DatatypeConverter.printHexBinary(bobKeyAgree.generateSecret()));
//        // RESPOND hex(bobPubEncoded)
//        // Alice derives secret
//        KeyFactory aliceKf = KeyFactory.getInstance("EC");
//        PublicKey remoteBobPub = aliceKf.generatePublic(new X509EncodedKeySpec(bobPubEncoded));
//        KeyAgreement aliceKeyAgree = KeyAgreement.getInstance("ECDH");
//        aliceKeyAgree.init(alicePvt);
//        aliceKeyAgree.doPhase(remoteBobPub, true);
//        System.out.println("Alice secret: " + DatatypeConverter.printHexBinary(aliceKeyAgree.generateSecret()));
    }
}
