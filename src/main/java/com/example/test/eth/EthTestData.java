package com.example.test.eth;

import cn.hutool.json.JSONUtil;
import org.junit.Test;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

/**
 * @author: lp
 * @create: 2023-08-25 13:24
 **/
public class EthTestData {

    //    {"address":"0xe81128942ed67a3b453576cad44fa9fb7f0b2098","privateKey":"8ca3edaabc0567d9555ade455bab24a27bea6ee0524e96ffac9a3cfc2b841214"}
    private String privateKey="8ca3edaabc0567d9555ade455bab24a27bea6ee0524e96ffac9a3cfc2b841214";

    private String myAddress = "0xD9e3bA5e70cC4e0738FA277426a31119c3B9E95C";
    //rinkeby上面的测试币 erc20-usdt同款
    private String contract="0xdac17f958d2ee523a2206206994597c13d831ec7";

    private Web3j web3j ;

    {
        try{
            //如果这个地址不知道怎么获取 可以参考  https://blog.csdn.net/sail331x/article/details/115395131
            web3j = Web3j.build(new HttpService("https://linea-mainnet.infura.io/v3/3a2506736a1d4bc88bbaec356dbfde37"));
        }catch (Throwable t){
            t.printStackTrace();
        }
    }


    /**
     * 查询ERC20数量
     */
//    @Test
    public void balanceOfErc20(){
        System.out.println("查询ERC20:"+ERC20Util.balanceOfErc20(web3j,contract,myAddress));
    }



    /**
     * 查询eth数量
     */
//    @Test
    public void balanceOf(){
        System.out.println("查询ETH:"+ERC20Util.balanceOf(web3j,myAddress));
    }



    /**
     * 创建地址
     */
//    @Test
    public void createAddress(){
        System.out.println("创建地址:"+ JSONUtil.toJsonStr(ERC20Util.createAddress()));
    }


    public static void main(String[] args) {

    }

}
