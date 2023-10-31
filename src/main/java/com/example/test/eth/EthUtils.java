package com.example.test.eth;

import lombok.extern.slf4j.Slf4j;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * @author lp
 * @date 2021-10-12 10:02
 */
@Slf4j
public class EthUtils {

    /**
     * 合约IP地址
     */
//    public static final String IP = "192.168.50.123";
//    public static final String IP = "192.168.53.41";
    public static final String IP = "192.168.10.204";
    public static final String PORT = "8646";
//    public static final String PORT = "7545";

    /**
     * 账号地址
     */
    private static final String MY_ADDRESS = "0xD9e3bA5e70cC4e0738FA277426a31119c3B9E95C";


    /**
     * 以太坊USDT
     */
    public static final String CONTRACT_ADDRESS = "0xdac17f958d2ee523a2206206994597c13d831ec7";

//
//    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
//        Web3j web3j = Web3j.build(new HttpService("https://linea-mainnet.infura.io/v3/3a2506736a1d4bc88bbaec356dbfde37"));
//        //需要SSL
////        Web3j web3j = Web3j.build(new HttpService("https://mainnet.infura.io/v3/3a2506736a1d4bc88bbaec356dbfde37"));
//        BigInteger balance = web3j.ethGetBalance(MY_ADDRESS, BalanceUnit.Wei).send().getBalance();
//    }





        //获取ETH的最新区块号
//        BigInteger blockNumber = web3j.ethBlockNumber().send().getBlockNumber();

//        List<EthBlock.TransactionResult> txs = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, true).send().getBlock().getTransactions();
//        txs.forEach(tx -> {
//            EthBlock.TransactionObject transaction = (EthBlock.TransactionObject) tx.get();
//            System.out.println("from:"+transaction.getFrom());
//            System.out.println("to:"+transaction.getTo());
//            System.out.println("value:"+transaction.getValue());
//            //16进制转10进制
//            int i = hexToInt(transaction.getValue().toString());
//            System.out.println("i:"+i);
//
//
//            System.out.println("input:"+transaction.getInput());
//        });


//        //通过区块号获取交易
//        List<EthBlock.TransactionResult> ethGetBlance = web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(blockNumber.subtract(new BigInteger("3"))), true).send().getBlock().getTransactions();
//        //通过hash获取交易
//        Optional<Transaction> transactions = web3j.ethGetTransactionByHash("hash").send().getTransaction();
//
//        List<EthBlock.TransactionResult> txs = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, true).send().getBlock().getTransactions();
//        txs.forEach(tx -> {
//            EthBlock.TransactionObject transaction = (EthBlock.TransactionObject) tx.get();
//            System.out.println(transaction.getFrom());
//            System.out.println(transaction.getTo());
//            System.out.println(transaction.getInput());
//        });
//    }


//    BigInteger latestBlock;
//        try {
//        //获取ETH的最新区块号
//        latestBlock = web3j.ethBlockNumber().send().getBlockNumber();
//        //通过区块号获取交易
//        List<EthBlock.TransactionResult> ethGetBlance = web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(latestBlock.subtract(new BigInteger("3"))),true).send().getBlock().getTransactions();
//        EthBlock.TransactionResult transactionResult = ethGetBlance.get(0);
//        System.out.println(transactionResult.get().toString());
//
//
//        //通过hash获取交易
//        Optional<Transaction> transactions = web3j.ethGetTransactionByHash("hash").send().getTransaction();
//        System.out.println(transactions);
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
//    List<EthBlock.TransactionResult> txs = null;
//        try {
//        //也可以直接获取最新交易
//        txs = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, true).send().getBlock().getTransactions();
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
//        txs.forEach(tx -> {
//        EthBlock.TransactionObject transaction = (EthBlock.TransactionObject) tx.get();
//        System.out.println(transaction.getFrom());
//        System.out.println(transaction.getTo());
//        System.out.println(transaction.getInput());
//    });


//    public static void main(String[] args) throws Exception {
//        String tskId="2hgpEIGNqJz4kADL/OfsMBIOn8k=";
//        byte[] decode = Base64Utils.decodeFromString(tskId);
//        String str=new String(decode);
//        System.out.println(str);

//        Web3j web3 = Web3j.build(new HttpService("https://mainnet.infura.io/v3/3a2506736a1d4bc88bbaec356dbfde37"));  // defaults to http://localhost:8545/
//        Web3j web3 = Web3j.build(new HttpService("https://linea-mainnet.infura.io/v3/3a2506736a1d4bc88bbaec356dbfde37"));  // defaults to http://localhost:8545/
//        BigInteger blockNumber = web3.ethBlockNumber().send().getBlockNumber();
//        System.out.println("当前区块高度:" + blockNumber);
//
//        List<EthBlock.TransactionResult> ethGetBlance = web3.ethGetBlockByNumber(DefaultBlockParameter.valueOf(blockNumber.subtract(new BigInteger("3"))), true).send().getBlock().getTransactions();
//        Optional<Transaction> transactions = web3.ethGetTransactionByHash("hash").send().getTransaction();
//        List<EthBlock.TransactionResult> txs = web3.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, true).send().getBlock().getTransactions();
//        System.out.println(txs);
//        txs.forEach(tx -> {
//            EthBlock.TransactionObject transaction = (EthBlock.TransactionObject) tx.get();
//
//            System.out.println("from:" + transaction.getFrom());
//            System.out.println("to:" + transaction.getTo());
//            System.out.println("money:" + transaction.getValueRaw());
//            String bigInteger = Numeric.encodeQuantity(transaction.getValue());
//
//
//            System.out.println("bigInteger:"+bigInteger);
//            System.out.println("valueRaw:" + transaction.getValueRaw());
//        });

//    }
//
//// send asynchronous requests to get balance
//        EthGetBalance ethGetBalance = web3
//                .ethGetBalance("0xD9e3bA5e70cC4e0738FA277426a31119c3B9E95C", DefaultBlockParameterName.LATEST)
//                .sendAsync()
//                .get();
//        BigInteger wei = ethGetBalance.getBalance();
//        //            // 将wei转换为以太币（ETH）单位
//        BigDecimal etherBalance = Convert.fromWei(new BigDecimal(wei), Convert.Unit.ETHER);
//
//        System.out.println("账户地址：" + "0xD9e3bA5e70cC4e0738FA277426a31119c3B9E95C");
//        System.out.println("以太坊余额：" + etherBalance + " ETH");
//
//    }

    //} catch (IOException e) {e.printStackTrace();}}

    public static void main(String[] args) {
        // 连接到以太坊网络
        Web3j web3j = Web3j.build(new HttpService("https://linea-mainnet.infura.io/v3/3a2506736a1d4bc88bbaec356dbfde37"));




//        try {
//            // 查询账户余额
//            BigInteger weiBalance = web3j
//                    .ethGetBalance("THFAAhveUqs2vvPiP8pwmjjwk6qxyEcCMd", DefaultBlockParameterName.LATEST)
//                    .send()
//                    .getBalance();
//
//            // 将wei转换为以太币（ETH）单位
//            BigDecimal etherBalance = Convert.fromWei(new BigDecimal(weiBalance), Convert.Unit.ETHER);
//
//            System.out.println("账户地址：" + MY_ADDRESS);
//            System.out.println("以太坊余额：" + etherBalance + " ETH");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }


//    public static void main(String[] args) throws IOException {
//        //  合约签名方法0x70a08231是查询余额的
//        String DATA_PREFIX = "0x70a08231000000000000000000000000";
//        //  合约签名方法0x313ce567是查询decimals的
//        String DATA_DECIMAL_PREFIX = "0x313ce567000000000000000000000000";
//        // USDT ERC-20的合约地址
//        String CONTRACT_ADDRESS = "0xdaC17f958D2eE523A2206206994597c13D831ec7";
//        // 查询地址
//        String address = "0xE5E4f085c81342529b650c5f0E792980D673942B";
//        // 获取余额
//        String value = Admin.build(new HttpService("https://linea-mainnet.infura.io/v3/3a2506736a1d4bc88bbaec356dbfde37"))
//                .ethCall(org.web3j.protocol.core.methods.request.Transaction.createEthCallTransaction(address,
//                        CONTRACT_ADDRESS, DATA_PREFIX + address.substring(2)), DefaultBlockParameterName.PENDING).send().getValue();
//
//        System.out.println("value:"+value);
//        if(value.equals("0x")){
//            value="0x00";
//        }
//        // 16进制转10进制
//        String valueStr = new BigInteger(value.substring(2), 16).toString();
//
//        // 获取decimals
//        String decimals = Admin.build(new HttpService("https://linea-mainnet.infura.io/v3/3a2506736a1d4bc88bbaec356dbfde37"))
//                .ethCall(org.web3j.protocol.core.methods.request.Transaction.createEthCallTransaction(address,
//                        CONTRACT_ADDRESS, DATA_DECIMAL_PREFIX + address.substring(2)), DefaultBlockParameterName.PENDING).send().getValue();
//        System.out.println(decimals);
//        if(decimals.equals("0x")){
//            decimals="0x00";
//        }
//        String decimalStr = new BigInteger(decimals.substring(2), 16).toString();
//        // 10的6次方
//        BigDecimal WEI = new BigDecimal(Math.pow(10, Double.parseDouble(decimalStr)));
//        BigDecimal balance = new BigDecimal(valueStr).divide(WEI, Integer.parseInt(decimalStr), RoundingMode.HALF_DOWN);
//        System.out.println(balance);
//    }

    private static final BigDecimal WEI = new BigDecimal(10000);


    /**
     * 16进制转10进制
     *
     * @param s 16进制字符串
     * @return 返回10进制数字
     */
    public static int hexToInt(String s) {
        byte[] bytes = Numeric.hexStringToByteArray(s);
        BigInteger bigInteger = Numeric.toBigInt(bytes);
        return bigInteger.intValue();
    }


    /**
     * 获取当前最新区块号
     */
    public static BigInteger getBlockNumber(Web3j web3j) {
        EthBlockNumber send;
        try {
            send = web3j.ethBlockNumber().send();
            return send.getBlockNumber();
        } catch (IOException e) {
            log.info("请求区块链信息异常 >> 区块数量,{}", e.getMessage());
        }
        return null;
    }


}
