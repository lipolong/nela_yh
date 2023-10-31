package com.example.test.test;

import com.google.common.collect.ImmutableList;
import org.bitcoinj.crypto.*;
import org.bitcoinj.wallet.DeterministicSeed;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;

import java.security.SecureRandom;
import java.util.List;

/**
 * @author lp
 * @date 2022-02-10 17:02
 */
public class Wallet {

    /**
     * path路径
     */
    private final static ImmutableList<ChildNumber> BIP44_ETH_ACCOUNT_ZERO_PATH =
            ImmutableList.of(new ChildNumber(44, true), new ChildNumber(60, true),
                    ChildNumber.ZERO_HARDENED, ChildNumber.ZERO);

    public static void main(String[] args) throws Exception {
        //{"resourceType":5,"title":"Www","description":"XX by"}
//        String str="{\"resourceType\":5,\"title\":\"Www\",\"description\":\"XX by\"}";
//        System.out.println(str.length());
        //创建钱包
        createWallet();
    }

    /**
     * 创建钱包
     * @throws MnemonicException.MnemonicLengthException
     */
    private static void createWallet()  throws MnemonicException.MnemonicLengthException {
        SecureRandom secureRandom = new SecureRandom();
        byte[] entropy = new byte[DeterministicSeed.DEFAULT_SEED_ENTROPY_BITS / 8];
        secureRandom.nextBytes(entropy);

        //生成12位助记词
        List<String> str = MnemonicCode.INSTANCE.toMnemonic(entropy);
        StringBuilder sb = new StringBuilder();
        for(String s:str){
            sb.append(s).append(" ");
        }
        sb.setLength(sb.length() - 1);

        //使用助记词生成钱包种子
        byte[] seed = MnemonicCode.toSeed(str, "");
        DeterministicKey masterPrivateKey = HDKeyDerivation.createMasterPrivateKey(seed);
        DeterministicHierarchy deterministicHierarchy = new DeterministicHierarchy(masterPrivateKey);
        DeterministicKey deterministicKey = deterministicHierarchy
                .deriveChild(BIP44_ETH_ACCOUNT_ZERO_PATH, false, true, new ChildNumber(0));
        byte[] bytes = deterministicKey.getPrivKeyBytes();
        ECKeyPair keyPair = ECKeyPair.create(bytes);
        //通过公钥生成钱包地址
        String address = Keys.getAddress(keyPair.getPublicKey());

        System.out.println();
        System.out.println("助记词：");
        System.out.println(sb.toString());
        System.out.println();
        System.out.println("地址：");
        System.out.println(Keys.toChecksumAddress(address));
        System.out.println();
        System.out.println("私钥：");
        System.out.println(keyPair.getPrivateKey().toString(16));
        System.out.println();
        System.out.println("公钥：");
        System.out.println(keyPair.getPublicKey().toString(16));





    }


}
