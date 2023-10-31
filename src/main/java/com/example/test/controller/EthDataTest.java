package com.example.test.controller;

import jnr.ffi.annotations.In;
import org.apache.tomcat.util.buf.HexUtils;
import org.bouncycastle.util.encoders.Hex;
import org.bouncycastle.util.encoders.HexEncoder;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.protocol.Web3j;
import org.web3j.utils.Assertions;
import org.web3j.utils.Numeric;
import org.web3j.utils.Strings;
import sun.security.provider.SHA;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

/**
 * @author lp
 * @date 2022-05-18 16:43
 */
public class EthDataTest {
    /**
     * 获取data
     */
    public static final String DATA ="0x0000000000000000000000000000000000000000000000000000000000000050000000000000000000000000f1dc7cccb03c77f32bd8ece0099edb91e47bdbea00000000000000000000000000000000000000000000000000000000000000cd0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000058d15e17628000000000000000000000000000000000000000000000000000000000000000000040000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000006225a5e8000000000000000000000000000000000000000000000000000000006225a5e80000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000016000000000000000000000000000000000000000000000000000000000000000377b227265736f7572636554797065223a312c227469746c65223a22e5bab7e5b7b47e222c226465736372697074696f6e223a227436227d000000000000000000";
    /**
     * 开始位置
     */
    public static final int START_SIZE =0;
    /**
     * 结束位置
     */
    public static final int END_SIZE =64;


    public static void main(String[] args) {
        String str="76047455486137605890905483468085779856248426895049058727816774433969524661740760474554861376058909054834680857798562484268950490587278167744339695246617401";




//        Event event = getSoldRecordEvent();
//        String str="0x000000000000000000000000000000000000000000000000000000000000003a0000000000000000000000002dffe9161e1e07b70b2c39be6e98d15df48e0eff0000000000000000000000000000000000000000000000000000000000000001000000000000000000000000000000000000000000000000000000000000014000000000000000000000000000000000000000000000000000000000628dfcac000000000000000000000000000000000000000000000000000000000000005900000000000000000000000000000000000000000000000000000000000000330000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000628370ac000000000000000000000000000000000000000000000000000000000000009e33303444303330323037303030323031323030323231303042384241334634373431434344303839353031394142344141343443314543443246374543323142314636444236343933394632384330383135364433443835303232313030434532433745433535374338323835363645304243334333363130354241324439353743393934463939383345314434433035453634384437373946363732450000";
//        List<Type> results = FunctionReturnDecoder.decode(str, event.getParameters());
//        String userPub = (String) results.get(3).getValue();
//        System.out.println(userPub);

//        NzYwNDc0NTU0ODYxMzc2MDU4OTA5MDU0ODM0NjgwODU3Nzk4NTYyNDg0MjY4OTUwNDkwNTg3Mjc4MTY3NzQ0MzM5Njk1MjQ2NjE3NDA3NjA0NzQ1NTQ4NjEzNzYwNTg5MDkwNTQ4MzQ2ODA4NTc3OTg1NjI0ODQyNjg5NTA0OTA1ODcyNzgxNjc3NDQzMzk2OTUyNDY2MTc0MDE=
//

//        BigInteger bigInteger = Numeric.decodeQuantity("0x1");
//        System.out.println(bigInteger);
//        //1.去除0x
//        String data = DATA.substring(2);
//        /*
//        newOrder的数据类型是预先就知道的(按照顺序来)：
//        Uint256，Address，Uint256，Uint8，Uint256，Uint8，Uint256，Uint256，Uint256，Uint8，Utf8String
//         */
//        //2.Uint256解析  0-64 转成10进制
//        int tokenId = getIntData(data.substring(START_SIZE, END_SIZE));
//        System.out.println(tokenId);


    }
    private static  Event getSoldRecordEvent() {
        return new Event(
                "NFTRecord",
                Arrays.<TypeReference<?>>asList(
                        new TypeReference<Uint256>(true) {
                        },
                        new TypeReference<Address>(true) {
                        },
                        new TypeReference<Uint8>(true) {
                        },
                        new TypeReference<Utf8String>(true) {
                        },
                        new TypeReference<Uint256>(true) {
                        },
                        new TypeReference<Uint256>(true) {
                        },
                        new TypeReference<Uint256>(true) {
                        },
                        new TypeReference<Address>(true) {
                        },
                        new TypeReference<Uint256>(true) {
                        },
                        new TypeReference<Uint256>(true) {
                        }
                )
        );}

    public static Integer getIntData(String str){
        BigInteger bigint=new BigInteger(str, 16);
        return bigint.intValue();
    }



}
