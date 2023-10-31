package com.example.test.api;

import com.example.test.api.util.ExChangeUtils;
import com.example.test.util.HttpClient;
import com.example.test.util.StringJSONUtil;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author: lp
 * @create: 2023-06-10 16:03
 * <p>
 * 交易API接口测试类:
 * 1.交易方必须在平台添加账号,添加收款方式:微信,支付宝,银行卡 选一个
 * 2.生成账号之后会有MD5专用秘钥用于sign加密
 * 3.交易订单号由平台生成,产生订单后会返回,交易方注意自己保存
 * 4.交易方通过API交易,平台只负责记录订单,并不判断资产余额是否足够,凡是通过API交易的都不做余额判断(注意一下)
 * 5.交易使用USDT(美元单价),会返回人民币与美元汇率(交易付款时注意是人民币面值而非没有价格),平台也会计算,汇率每个一段时间会根据市场变化而改变,请注意
 * 6.暂时平台只开放 USDT挂单买卖,暂不支持币币兑换
 * 7.工具类位置: com.example.test.api.util(其它包可以不用管)
 * 8.交易可能会出现:请求数据过多,请稍后重试! 那是因为挂单的商家购买数量有限,为了保证数量不会出错会触发分布式锁,稍后重试就可以了
 **/
public class ApiTest {
    //测试账号
    public static final String merName = "etanges";
    //测试秘钥
    public static final String secret = "b9fd13e9e8e38c59";

    public static final String serverUrl = "http://localhost:8080/xrt";


    public static void main(String[] args) {
        //1.获取挂单-出售商家列表
//        saleOrderList();
        //1.1.获取挂单-出售商家列表 --API购买交易
//        exchangeSaleOrder();
        //2.获取挂单-购买商家列表
//        buyOrderList();
        //2.1.获取挂单-购买商家列表 --- API出售交易
//        exchangeBuyOrder();
        //3.查询订单接口
//        queryByOrderNo();

    }


    /**
     * 获取异步回调信息
     * 返回参数:
     * orderNo:订单号
     * queryTime :本次回调的时间戳
     * status:  订单状态:0进行中 1已完成 2已撤销 3已超时 4已付款
     * buyName : 购买方
     * salerName:出售方
     * finishTime:结束时间
     * orderTime:开始时间
     * payPicUrl : 交易截图
     * sign:签名
     *
     *
     * @return 返回
     */
    @RequestMapping("/notify")
    @ResponseBody
    public String getNotify(HttpServletRequest request){
        //获取参数
        Map<String,Object> map= getParameterMap(request);
        System.out.println("获取参数:"+map);
        //验签
        String sign = (String) map.get("sign");
        map.remove("sign");
        //true 验签正确 false 验签失败
        boolean b = ExChangeUtils.checkSign(map, secret, sign);
        System.out.println(b);
        //TODO 业务
        return "SUCCESS";
    }




    /*
    查询订单:
    {
"success": true,
"message": "",
"code": 200,  //200成功 其它失败
"result": {
"unitPrice": "10", //交易单价(美元)
"coinType": 3, //3:USDT币种类型 其它未开放
"orderType": 1, //支付方式 1:支付宝 2:微信 3:银行卡
"finishTime": null, //订单完成时间
"orderNo": "zfb20202029090", //订单号
"salerName": "etanges", //购买方
"quantity": "10", //交易数量
"payPicUrl": "http://192.168.3.2:8080/xrt/api/common/staticnull", //支付截图
"totalPrice": "100.00", //总金额(USDT)
"totalPriceCNY": "712.80",//总金额 人民币
"buyName": "test1",//购买方
"sign": "c99a4d4a497abf532d0bf55bccd37de6",//签名
"mserName": "etanges", //当前请求API商家名称
"orderTime": "2023-06-11 18:19:01", //创建订单时间
"rate": "7.128", //本次交易的汇率(美元-->人民币)
"queryTime": 1686666853544,(请求返回时间戳)
"status": 0 //0进行中 1已完成 2已撤销 3已超时 4已付款
//如果 paymentType=1时出现 支付宝的
"zfbAccount":"123", //支付宝账号
"zfbAccountName":"124"//支付宝名称
 //如果paymentType=2时出现 微信的
"wxAccount":"12312",
"wxAccountName":"12321"
//如果paymentType=3时(出现) 银行卡
"userName":"aaa", //持卡人姓名
 "phonenumber":"12312",//绑卡手机号码
"cardNo":"37872498732498327489",//卡号
"bankName":"泉州银行" //开户行
},
"timestamp": 1686666853571
}
     */
    public static void queryByOrderNo() {
        String url = serverUrl + "/api/exchange/queryByOrderNo";
        Map<String, Object> map = new HashMap<>();
        //商家名称
        map.put("merName", merName);
        //订单号
        map.put("orderNo", "zfb20202029090");
        //时间戳
        map.put("queryTime", System.currentTimeMillis());
        map.put("sign", ExChangeUtils.getSign(map, secret));
        JSONObject jsonObject = JSONObject.fromObject(map);
        JSONObject object = HttpClient.doJsonPost(url, jsonObject.toString());
        System.out.println(object);
    }


    /*
    1.1.获取挂单-出售商家列表 --API购买交易

            返回参数:  获取结果可以使用查询
    {
"success": true,
"message": "交易进行中,请稍后查询!",
"code": 200,
"result": "交易进行中,请稍后查询!",
"timestamp": 1686478740480
}
     */
    public static void exchangeSaleOrder() {
        String url = serverUrl + "/api/exchange/exchangeSaleOrder";
        Map<String, Object> map = new HashMap<>();
        //商家名称
        map.put("merName", merName);
        //购买的数量
        map.put("buyQuantity", "10");
        //时间戳
        map.put("queryTime", System.currentTimeMillis());
        //挂单列表返回的汇率(人民币对美元)
        map.put("changeRate", "7.128");
        //订单号
        map.put("orderNo", "TS" + System.currentTimeMillis());
        //购买交易结果回调地址:当挂单卖家确认收款时,反馈给API请求方
        map.put("buyNotifyUrl", "https://www.google.com");
        //如果有值则表示与某个挂单商家交易, 如果没有值 表示需要系统撮合
        map.put("id", "");
        map.put("sign", ExChangeUtils.getSign(map, secret));
        JSONObject jsonObject = JSONObject.fromObject(map);
        JSONObject object = HttpClient.doJsonPost(url, jsonObject.toString());
        System.out.println(object);

    }


    /*

    获取挂单-购买商家列表 --- API出售交易
    返回参数:  获取结果可以使用查询
    {
"success": true,
"message": "交易进行中,请稍后查询!",
"code": 200,
"result": "交易进行中,请稍后查询!",
"timestamp": 1686478740480
}
     */

    public static void exchangeBuyOrder() {
        String url = serverUrl + "/api/exchange/exchangeBuyOrder";
        Map<String, Object> map = new HashMap<>();
        //商家名称
        map.put("merName", merName);
        //出售的数量
        map.put("saleQuantity", "10");
        //时间戳
        map.put("queryTime", System.currentTimeMillis());
        //挂单列表返回的汇率(人民币对美元)
        map.put("changeRate", "7.128");
        //当前商家的收款方式: 1:支付宝 2:微信 3:银行卡
        map.put("payType", 1);
        //订单号
        map.put("orderNo", "TS" + System.currentTimeMillis());
        //出售交易结果回调地址:当挂单买家确认付款并截图时,反馈给API请求方
        map.put("saleNotifyUrl", "https://www.google.com");
        //如果有值则表示与某个挂单商家交易, 如果没有值 表示需要系统撮合
        map.put("id", "");
        map.put("sign", ExChangeUtils.getSign(map, secret));
        JSONObject jsonObject = JSONObject.fromObject(map);
        JSONObject object = HttpClient.doJsonPost(url, jsonObject.toString());
        System.out.println(object);
    }


    /*
        获取挂单-购买商家列表
        {
            "success":true,
            "message":"",
            "code":200,  //200成功 其它失败
            "result":[
                {
                    "id":5, //挂单购买ID
                    "loginName":"test", //挂单购买商家名称
                    "buyAmount":"10", //挂单购买单价(美元)
                    "buyCNYAmount":"71.50", //挂单购买单价(人民币)
                    "buyQuantity":"100", //购买数量
                    "dealQuantity":"0", //已交易数量
                    "minBuyQuantity":"100", //单次最小交易数量
                    "maxBuyQuantity":"1000", //单次最大交易数量
                    "coinType":3, //暂时只支持 3:USDT
                    "coinName":"USDT", //购买币种
                    "rate":"7.1498"//人民币与美元兑换汇率
                }
            ],
            "timestamp":1686648738854
        }
     */
    public static void buyOrderList() {
        String url = serverUrl + "/api/exchange/buyOrderList";
        String respData = HttpClient.doGet(url);
        System.out.println(respData);
        //转成json获取
        JSONObject object = JSONObject.fromObject(respData);
        //TODO 然后取值
    }


    /**
     * 获取挂单-出售商家列表
     * 返回数据:
     * {
     * *     "success":true,//可以忽略
     * *     "message":"", //错误时返回:错误原因
     * *     "code":200, //200代表请求成功 其它失败
     * *     "result":[
     * *         {
     * *             "id":10,  //挂单出售ID
     * *             "loginName":"test1", //挂单商家名称
     * *             "saleAmount":"20", //挂单出售单价(美元价格(USDT))
     * *             "saleCNYAmount":"143.00",//挂单出售单价(人民币单价)
     * *             "saleQuantity":"180", //挂单出售的数量
     * *             "dealQuantity":"0", //挂单出售在交易/已出售数量
     * *             "minSaleQuantity":"10", //最小交易数量(API不控制)
     * *             "maxSaleQuantity":"180", // 最大交易数量(API不控制)
     * *             "coinType":3,
     * *             "coinName":"USDT", //出售币种类型
     * *             "paymentType":1, //挂单商家收款方式:1:支付宝 2:微信 3:银行卡 根据类型的不同显示不同的(infoAccount)数据
     * *             "rate":"7.1498",
     * *             "infoAccount":{
     * *
     * *                  //如果 paymentType=1时出现 支付宝的
     * *                 "zfbAccount":"123", //支付宝账号
     * *                 "zfbAccountName":"124"//支付宝名称
     * *                 //如果paymentType=2时出现 微信的
     * *                 "wxAccount":"12312",
     * *                 "wxAccountName":"12321"
     * *                 //如果paymentType=3时(出现) 银行卡
     * *                 "userName":"aaa", //持卡人姓名
     * *                 "phonenumber":"12312",//绑卡手机号码
     * *                 "cardNo":"37872498732498327489",//卡号
     * *                 "bankName":"泉州银行" //开户行
     * *             }
     * *         }
     * *     ],
     * *     "timestamp":1686646494532 //返回时间戳
     * * }
     */
    public static void saleOrderList() {
        String url = serverUrl + "/api/exchange/saleOrderList";
        String respData = HttpClient.doGet(url);
        System.out.println(respData);
        //转成json获取
        JSONObject object = JSONObject.fromObject(respData);
        //TODO 然后取值
    }


    public static Map<String, Object> getParameterMap(HttpServletRequest request) {
        // 参数Map
        Map<String, String[]> properties = request.getParameterMap();
        // 返回值Map
        Map<String, Object> returnMap = new HashMap<>();
        Iterator<Map.Entry<String, String[]>> entries = properties.entrySet().iterator();
        Map.Entry<String, String[]> entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = entries.next();
            name = entry.getKey();
            Object valueObj = entry.getValue();
            if(null == valueObj){
                value = "";
            }else {
                String[] values = (String[])valueObj;
                for (String s : values) {
                    value = s + ",";
                }
                value = value.substring(0, value.length()-1);
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }

}
