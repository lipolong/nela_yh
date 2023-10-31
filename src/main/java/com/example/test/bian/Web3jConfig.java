package com.example.test.bian;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

/**
 * @author: lp
 * @create: 2023-08-24 16:58
 * 初始化web3j
 **/
//@Component
public class Web3jConfig {

    String URL = "https://linea-mainnet.infura.io/v3/3a2506736a1d4bc88bbaec356dbfde37";

    private static Web3j web3j = null;


    /**
     * 实例化web3j
     * @return 返回
     */
    private synchronized Web3j init() {
        if (!ObjectUtils.isEmpty(web3j)) {
            return web3j;
        }
        web3j = Web3j.build(new HttpService(URL));
        return web3j;
    }

    /**
     * 获取web3j
     * @return 返回
     */
    public Web3j getWeb3j() {
        if (!ObjectUtils.isEmpty(web3j)) {
            return web3j;
        }
        return init();
    }


}
