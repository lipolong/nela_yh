package com.example.test.controller;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.net.URLDecoder;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.defipay.api.client.DefipayApiClientFactory;
import com.defipay.api.client.DefipayApiRestClient;
import com.defipay.api.client.config.Env;
import com.defipay.api.client.domain.ApiResponse;
import com.defipay.api.client.domain.request.CreateOrderRequest;
import com.defipay.api.client.domain.response.CreateOrderResponse;
import com.defipay.api.client.impl.LocalSigner;
import com.example.test.ecdh.DHCoder;
import com.example.test.eth.EthUtils;
import com.example.test.init.InitApplicationData;
import com.example.test.model.InitConfig;
import com.example.test.util.Base64Util;
import com.example.test.util.StringUtil;
import com.google.common.util.concurrent.RateLimiter;
import io.edfs.tskandroidv2.TSKJni;
import kotlin.jvm.internal.Intrinsics;
import org.apache.http.entity.StringEntity;
import org.apache.kafka.common.protocol.types.Field;
import org.spongycastle.util.encoders.Hex;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.Hash;
import org.web3j.protocol.websocket.WebSocketClient;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * @author lp
 * @date 2021-11-04 13:48
 */
@RestController
@RequestMapping("/test")
public class TestSoController {

    //    //项目启动之前加载TSK初始化
//    static {
//        if (System.getProperty("os.name").startsWith("linux")
//                || System.getProperty("os.name").startsWith("Linux")
//        ) {
//            new InitConfig();
//        }
//    }
    public static final String apiSecret = "69a8b94508f9d68fccd04d7b30a1493cefe3dbd412086a6c4a53a66a6c6e626d";

    public static final DefipayApiRestClient client = DefipayApiClientFactory.newInstance(
            new LocalSigner(apiSecret),
    Env.SANDBOX,
            true).newRestClient();


    /**
     * 限流策略 ： 1秒钟2个请求
     */
    private final RateLimiter limiter = RateLimiter.create(2.0);

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @RequestMapping("/test001")
    public Object test001(){
        //500毫秒内，没拿到令牌，就直接进入服务降级
        boolean tryAcquire = limiter.tryAcquire(500, TimeUnit.MILLISECONDS);
        if (!tryAcquire) {
            System.out.println("进入服务降级，时间:"+ LocalDateTime.now().format(dtf));
            return "当前排队人数较多，请稍后再试！";
        }
        return "请求成功";
    }

    public static void main(String[] args) {
        //tskId:da182910818da89cf89000cbfce7ec30120e9fc9
//        String str="2hgpEIGNqJz4kADL/OfsMBIOn8k=";


        //304c03020700020120022011df9d32fdeab7c1fa37cdfb1c4991642d784c8202c758ab44188636fda119f3022100a48f96161675915ee216d9d30c42f3e9f8da1a6f8dc26b350a330e7130808943
//        String str="MEwDAgcAAgEgAiAR350y/eq3wfo3zfscSZFkLXhMggLHWKtEGIY2/aEZ8wIhAKSPlhYWdZFe4hbZ0wxC8+n42hpvjcJrNQozDnEwgIlD";


    //306e03020780020120022011df9d32fdeab7c1fa37cdfb1c4991642d784c8202c758ab44188636fda119f3022100a48f96161675915ee216d9d30c42f3e9f8da1a6f8dc26b350a330e71308089430220735ba9466c54ce611cc7e7f2d490ac919b44745505c7e2711dbac1db74933c52

        CreateOrderRequest request = new CreateOrderRequest();
        request.setNotifyUrl("http://xcsewvb.ao/nhhcn");
        request.setReturnUrl("http://xcsewvb.ao/nhhcn");
        request.setAmount("0.01");
        request.setCurrency("ETH");
        request.setMemberTransNo("testasdafasf001");
        ApiResponse<CreateOrderResponse> order = client.createOrder(request);

        System.out.println(order.getErrorMessage());
//        String str="MG4DAgeAAgEgAiAR350y/eq3wfo3zfscSZFkLXhMggLHWKtEGIY2/aEZ8wIhAKSPlhYWdZFe4hbZ0wxC8+n42hpvjcJrNQozDnEwgIlDAiBzW6lGbFTOYRzH5/LUkKyRm0R0VQXH4nEdusHbdJM8Ug==";
//        DefipayApiRestClient client = DefipayApiClientFactory.newInstance(
//                new LocalSigner(apiSecret),
//                Env.SANDBOX,
//                false).newRestClient();
//        CreateOrderRequest request = new CreateOrderRequest();
//        request.setNotifyUrl("http://xcsewvb.ao/nhhcn");
//        request.setReturnUrl("http://xcsewvb.ao/nhhcn");
//        request.setAmount("0.01");
//        request.setCurrency("ETH");
//        request.setRedirectUrl("http://xcsewvb.ao/nhhcn");
//        request.setMemberTransNo("testasdafasf001");
//        ApiResponse<CreateOrderResponse> order = client.createOrder(request);
    }







    @GetMapping("/test111")
    public Object test(){
        String priKey = "d73b3b97de6386e7c1e28c7285244ac1b9bf3f1de01480548ad6153075548509";
        byte[] encode = Base64Util.encode(priKey.getBytes());
        byte[] bytes = TSKJni.DigestSha256Single(encode.length, encode);
        String pr = TSKJni.IdentityIssueExBase64(bytes, bytes.length);
        JSONObject jsonObject = JSONObject.parseObject(pr);
        Map<String,String> map = new HashMap<>();
        map.put("privateKey",HexUtil.encodeHexStr(Base64.getDecoder().decode(jsonObject.getString("PrivateKey"))));
        map.put("publicKey",HexUtil.encodeHexStr(Base64.getDecoder().decode(jsonObject.getString("PublicKey"))));
        map.put("tskId",HexUtil.encodeHexStr(Base64.getDecoder().decode(jsonObject.getString("KeyID"))));
        return map;
    }


    @PostMapping("/test")
    public Object tests(String params,String sign) throws Exception {
        System.out.println("params:"+params);
        System.out.println("sign:"+sign);
        Map<String,Object> map = new HashMap<>(16);
        map.put("params", params);
        map.put("sign", sign);
        //测试租赁
        return JSON.toJSONString(map.toString());
    }


    /**
     * 将数组转换成 int
     * @param bytes 数组
     * @return 返回
     */
    public static int bytesToInt(byte[] bytes){
        ByteBuffer allocate = ByteBuffer.allocate(8);
        allocate.put(bytes);
        return allocate.getInt();
    }

    /**
     * 截取数组位数
     * @param str 数组
     * @param begin 开始位置
     * @param destPos 从中复制数据的目标数组索引
     * @param count 长度
     * @return 返回
     */
    public static byte[] subBytes(byte[] str,int destPos,int begin,int count){
        byte[] bytes = new byte[count];
        System.arraycopy(str,begin,bytes,destPos,count);
        return bytes;
    }
}
