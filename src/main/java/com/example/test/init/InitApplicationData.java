package com.example.test.init;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.example.test.bian.Web3jConfig;
import com.example.test.eth.EthUtils;
import io.reactivex.disposables.Disposable;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.bouncycastle.util.encoders.Hex;
import org.java_websocket.enums.ReadyState;
import org.java_websocket.exceptions.WebsocketNotConnectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Keys;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.websocket.WebSocketClient;
import org.web3j.protocol.websocket.WebSocketService;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigInteger;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author lp
 * @date 2021-10-11 17:22
 */
@Component
public class InitApplicationData implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(InitApplicationData.class);
    public static WebSocketClient mWebSocketClient = null;



    @Override
    public void run(String... args) throws Exception {
        System.out.println("启动监听.......");
        Web3j web3j = Web3j.build(new HttpService("https://linea-mainnet.infura.io/v3/3a2506736a1d4bc88bbaec356dbfde37"));
        //获取ETH的最新区块号
        BigInteger blockNumber = web3j.ethBlockNumber().send().getBlockNumber();
        List<EthBlock.TransactionResult> txs = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, true).send().getBlock().getTransactions();
        txs.forEach(tx -> {
            EthBlock.TransactionObject transaction = (EthBlock.TransactionObject) tx.get();
            System.out.println("获取当前块的交易记录");
            System.out.println(transaction.getFrom());
            System.out.println(transaction.getTo());
            System.out.println(transaction.getInput());
            System.out.println(transaction.getValueRaw());
            System.out.println(transaction.getValue());
            System.out.println("结束获取当前块的交易记录");
        });


//        WebSocketClient mWebSocketClient = = new WebSocketClient(new URI("ws://" + EthUtils.IP + ":" + EthUtils.PORT));
//        initListenCreateNewOrderEvent(mWebSocketClient);
//        initListenCreateNewOrderEvent(mWebSocketClient);
//        minted(mWebSocketClient);
//        newOrder(mWebSocketClient);
//        setMinerEvent(mWebSocketClient);
//        getOriHashSet();
//        getStatusChange();
//        registerDappContractAddrEvt(mWebSocketClient);
//        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
//                new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());
//        executorService.scheduleAtFixedRate(() -> {
//            if (!mWebSocketClient.getReadyState().equals(ReadyState.OPEN)) {
//                mWebSocketClient.reconnect();
//                System.out.println("测试断线重连中。。。。。" + DateUtil.now());
//            }
//            if (mWebSocketClient.getReadyState().equals(ReadyState.OPEN)) {
//                System.out.println("测试链接成功。。。。。" + DateUtil.now());
//            }
//        }, 60, 30, TimeUnit.SECONDS);
    }






    /**
     * 事件
     * @return
     */
    public static Event getTransfer() {
        return new Event(
                "oriHashSet",
                Arrays.asList(
                        new TypeReference<Address>(true) {
                        },
                        new TypeReference<Address>(true) {
                        },
                        new TypeReference<Uint>(true) {
                        }
                ));
    }


    public static void getOriHashSet() {
        Web3j web3j = Web3j.build(new HttpService("http://192.168.53.41:8545"));
        EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST,"0xA1c2784cfc363AfE2Ba7FAF9C84b76D784e89f9c");
        Event event = getOriHashSetEvent();
        filter.addSingleTopic(EventEncoder.encode(event));
        web3j.ethLogFlowable(filter).subscribe(log -> {
//            System.out.println(log.toString());
            List<String> topics = log.getTopics();
            String owner = topics.get(1);
            String tokenId = topics.get(2);
            String oriHash = topics.get(3);
            Address address = new Address(owner);
            System.out.println("owner:"+ address.getValue());
            System.out.println("tokenId:"+ Numeric.decodeQuantity(tokenId));
            System.out.println("oriHash:"+oriHash);


            System.out.println("===========================================");
        });
    }


    public static void setMinerEvent(WebSocketClient mWebSocketClient){
        WebSocketService ws = new WebSocketService(mWebSocketClient, true);
        try {
            ws.connect();
            Web3j web3j = Web3j.build(ws);
            EthFilter filter = getFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, "0x0000000000000000000000000000000000003002");
            Event event = setMinerEvt();
            filter.addSingleTopic(EventEncoder.encode(event));
            web3j.ethLogFlowable(filter).subscribe(log -> {
                List<Type> results = FunctionReturnDecoder.decode(log.getData(), event.getParameters());
                byte[] value = (byte[]) results.get(0).getValue();
                System.out.println(value);
                String minerId = Hex.toHexString(value);
                System.out.println("minerId:0x" + minerId);
                List<Type> list = new ArrayList<>();
                Type<String> stringType = new Type<String>() {
                    @Override
                    public String getValue() {
                        return "0x"+minerId;
                    }
                    @Override
                    public String getTypeAsString() {
                        return "Bytes32";
                    }
                };
                list.add(stringType);
                //查询方法
                Function function = new Function("getMinerInfoByMinerId",
                        list
                        ,Arrays.asList(
                                new TypeReference<Utf8String>(true) {
                                },
                                new TypeReference<Utf8String>(true) {
                                },
                                new TypeReference<Utf8String>(true) {
                                },
                                new TypeReference<Utf8String>(true) {
                                }
                        ));

                // Get the latest nonce
                EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount("地址", DefaultBlockParameterName.LATEST).send();
                BigInteger nonce =  ethGetTransactionCount.getTransactionCount();
                //获取：gasPrice
                EthGasPrice gasPrice =  web3j.ethGasPrice().send();
                BigInteger gasLimit  = Convert.toWei("1", Convert.Unit.ETHER).toBigInteger();

                String encodedFunction = FunctionEncoder.encode(function);
                Transaction transaction = Transaction.createFunctionCallTransaction(
                        "发送地址",nonce,gasPrice.getGasPrice(),gasLimit ,"合约地址",encodedFunction
                );
                EthSendTransaction send = web3j.ethSendTransaction(transaction).sendAsync().get();
                System.out.println(send.getResult());
                System.out.println(send.getJsonrpc());
                System.out.println(send.getRawResponse());
                System.out.println(send.getId());

//                        byte[] signMessage=TransactionEncoder.signMessage(rawTransaction, chainId, credentials)
//                String hexValue = Numeric.toHexString(signMessage);
//                EthSendTransaction send1 = web3j.ethSendRawTransaction(hexValue).send();


                List<Type> someTypes = FunctionReturnDecoder.decode(send.getRawResponse(), function.getOutputParameters());

//                Web3j web3 = Web3j.build(new HttpService("http://localhost:8545"));
//                web3.ethSendRawTransaction()


//                org.web3j.protocol.core.methods.response.EthCall response = web3j.ethCall(
//                        Transaction.createEthCallTransaction(null, "0x0000000000000000000000000000000000003002", encodedFunction),
//                        DefaultBlockParameterName.LATEST).sendAsync().get();
//                //获取返回信息
//                List<Type> someTypes = FunctionReturnDecoder.decode(response.getValue(), function.getOutputParameters());
//                someTypes.forEach(
//                        a->{
//                            System.out.println("参数类型："+a.getTypeAsString());
//                            System.out.println("参数值："+a.getValue());
//                        }
//                );
            });
        } catch (ConnectException e) {
            logger.error("setMinerEvent连接失败：" + e.getMessage());
        }
    }


    public static void getminerInfoByid(Web3j web3j,EthFilter filter){
        Event event = setMinerInfoEvt();
        filter.addSingleTopic(EventEncoder.encode(event));
        web3j.ethLogFlowable(filter).subscribe(log -> {
            System.out.println(log.toString());
        });
    }




    public static String getMinerInfoByMinerId(String minerId){
        JSONObject jsonObject = new JSONObject();
        List<Map<String,String>> list = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        map.put("minerId",minerId);
        list.add(map);
        jsonObject.put("jsonrpc", "2.0");
        jsonObject.put("id", 1);
        jsonObject.put("method", "getMinerInfoByMinerId");
        jsonObject.put("params", list);
        String json = jsonObject.toString();
        String body = HttpRequest.get("http://192.168.50.123:8545").headerMap(getHeaderMap(), true).body(json).execute().body();
        JSONObject jsonBody = JSONObject.fromObject(body);
        return jsonBody.toString();
//        System.out.println("body:"+jsonBody.toString());
//        String address = jsonBody.getString("result");
//        return address;
    }

    public static Map<String, String> getHeaderMap() {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");
        headerMap.put("charset", "UTF-8");
        return headerMap;
    }

    public static void getStatusChange() {
        Web3j web3j = Web3j.build(new HttpService("http://192.168.50.123:8545"));
        EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, EthUtils.CONTRACT_ADDRESS);
        Event event = getStatusChangeEvent();
        filter.addSingleTopic(EventEncoder.encode(event));
        web3j.ethLogFlowable(filter).subscribe(log -> {
            System.out.println(log.toString());
            System.out.println(log.getData().length());
            List<Type> results = FunctionReturnDecoder.decode(log.getData(), event.getParameters());
            results.forEach(a -> {
                System.out.println(a.getTypeAsString() + ":" + a.getValue());
            });
        });
    }

    public static void recordOrder() {
        Web3j web3j = Web3j.build(new HttpService("http://192.168.50.123:8545"));
        EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, EthUtils.CONTRACT_ADDRESS);
        Event event = getSoldRecordEvent();
        Event event1 = getSoldRecordEvent1();
        filter.addSingleTopic(EventEncoder.encode(event));
        web3j.ethLogFlowable(filter).subscribe(log -> {
            System.out.println(log.toString());
            System.out.println(log.getData().length());
            List<Type> results = FunctionReturnDecoder.decode(log.getData(), event1.getParameters());
            results.forEach(a -> {
                System.out.println(a.getTypeAsString() + ":" + a.getValue());
            });
        });
    }

    public static void minted() throws URISyntaxException {
        String uri="ws://127.0.0.1:8546";
        WebSocketClient mWebSocketClient= new WebSocketClient(new URI(uri));
        WebSocketService ws = new WebSocketService(mWebSocketClient, true);
        try {
            ws.connect();
            Web3j web3j = Web3j.build(ws);
            EthFilter filter = getFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, "监听合约地址");
            //监听合约input参数一一对应 不然会监听失败
            Event event = new Event(
                    EventType.MINTED,
                    Arrays.asList(
                            new TypeReference<Address>(true) {
                            },
                            new TypeReference<Uint256>(true) {
                            },
                            new TypeReference<Bytes32>(true) {
                            }
                    ));
            filter.addSingleTopic(EventEncoder.encode(event));
            Disposable subscribe = web3j.ethLogFlowable(filter).subscribe(log -> {
                //处理返回的log信息
                System.out.println(log.toString());
                List<String> topics = log.getTopics();
                String bytes = topics.get(3);
                System.out.println(bytes);
            });
        } catch (ConnectException | WebsocketNotConnectedException e) {
            logger.error("minted：" + e.getMessage());
        }
    }

    public static void newOrder(WebSocketClient mWebSocketClient) {
        WebSocketService ws = new WebSocketService(mWebSocketClient, true);
        try {
            ws.connect();
            Web3j web3j = Web3j.build(ws);
            EthFilter filter = getFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, EthUtils.CONTRACT_ADDRESS);
            Event event1 = getNewOrderEvent1();
            filter.addSingleTopic(EventEncoder.encode(event1));
            Disposable subscribe = web3j.ethLogFlowable(filter).subscribe(log -> {
                System.out.println(log.toString());
//                System.out.println(log.getData().length());
                List<Type> results = FunctionReturnDecoder.decode(log.getData(), event1.getParameters());
                BigInteger orderType = (BigInteger) results.get(0).getValue();
                System.out.println("orderType:" + orderType);
                BigInteger tokenValue = (BigInteger) results.get(1).getValue();
                System.out.println("tokenValue:" + tokenValue);
                BigInteger leaseTime = (BigInteger) results.get(2).getValue();
                System.out.println("leaseTime:" + leaseTime);
                BigInteger createTime = (BigInteger) results.get(3).getValue();
                System.out.println("createTime:" + createTime);
                BigInteger orderStatus = (BigInteger) results.get(4).getValue();
                System.out.println("orderStatus:" + orderStatus);
                String transferHash = (String) results.get(5).getValue();
                System.out.println("transferHash:" + transferHash);
            });

        } catch (ConnectException | WebsocketNotConnectedException e) {
            logger.error("initListenCreateNewOrderEvent连接失败：" + e.getMessage());
            logger.error("尝试重连。。。。：");
        }
    }

    public void registerDappContractAddrEvt(WebSocketClient mWebSocketClient) {
        WebSocketService ws = new WebSocketService(mWebSocketClient, true);
        try {
            ws.connect();
            Web3j web3j = Web3j.build(ws);
            EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, "0x0000000000000000000000000000000000003002");
            Event event = getRegisterDappContractAddrEvt();
            filter.addSingleTopic(EventEncoder.encode(event));
            Disposable subscribe = web3j.ethLogFlowable(filter).subscribe(log -> {
                System.out.println(log.toString());
                List<Type> results = FunctionReturnDecoder.decode(log.getData(), event.getParameters());
                byte[] value = (byte[]) results.get(0).getValue();
                String s = Hex.toHexString(value);
                System.out.println("oriHash:0x" + s);
            });
        } catch (ConnectException | WebsocketNotConnectedException e) {
            logger.error("initListenSoldRecord连接失败：" + e.getMessage());
            logger.error("尝试重连。。。。：");
        }
//        Web3j web3j = Web3j.build(new HttpService("http://192.168.50.123:8545"));
//        EthFilter filter =new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, "0x0000000000000000000000000000000000003002");
//        Event event = getRegisterDappContractAddrEvt();
//        filter.addSingleTopic(EventEncoder.encode(event));
//        web3j.ethLogFlowable(filter).subscribe(log -> {
//            System.out.println(log.toString());
//            List<Type> results = FunctionReturnDecoder.decode(log.getData(), event.getParameters());
//            results.forEach(a-> {
//                System.out.println(a.getTypeAsString()+":"+a.getValue());
//            });
//        });
    }


    public void test() throws IOException, URISyntaxException {
        Web3j web3j = Web3j.build(new HttpService("http://localhost:8545"));
        //获取账户的 nonce
        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount("账号地址", DefaultBlockParameterName.LATEST).send();
        BigInteger nonce =  ethGetTransactionCount.getTransactionCount();
        //获取：gasPrice
        EthGasPrice gasPrice =  web3j.ethGasPrice().send();
        BigInteger value = Convert.toWei("1", Convert.Unit.ETHER).toBigInteger();


//        Transaction transaction = new Transaction();
//        web3j.ethSendTransaction()



    }

    public void record() throws ConnectException {
        Web3j web3j = Web3j.build(new HttpService("http://192.168.53.41:8545"));
//        EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, EthUtils.CONTRACT_ADDRESS);
//        Event event = getSoldRecordEvent();
//        filter.addSingleTopic(EventEncoder.encode(event));
//        web3j.ethLogFlowable(filter).subscribe(log -> {
//            System.out.println(log.toString());
//
//        });


        WebSocketService webSocketService = getWebSocketService(EthUtils.IP, EthUtils.PORT);
        webSocketService.connect();



//        Web3j web3j = Web3j.build(webSocketService);
//        EthFilter filter =new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, EthUtils.CONTRACT_ADDRESS);
//        Event event = getSoldRecordEvent();
//        filter.addSingleTopic(EventEncoder.encode(event));
//        web3j.ethLogFlowable(filter).subscribe(log -> {
//            System.out.println(log.toString());
//        });
    }

    public void initListenSoldRecord() {
        WebSocketService ws = new WebSocketService(mWebSocketClient, true);
        try {
            ws.connect();
            Web3j web3j = Web3j.build(ws);
            EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, EthUtils.CONTRACT_ADDRESS);
            Event event = getSoldRecordEvent();
            filter.addSingleTopic(EventEncoder.encode(event));
            Disposable subscribe = web3j.ethLogFlowable(filter).subscribe(log -> {
                System.out.println(log.toString());
                List<Type> results = FunctionReturnDecoder.decode(log.getData(), event.getParameters());
                results.forEach(System.out::println);
            });
            if (!subscribe.isDisposed()) {
                logger.info("开始监听【SoldRecord】事件成功!");
            } else {
                logger.info("开始监听【SoldRecord】事件失败！");
            }
        } catch (ConnectException | WebsocketNotConnectedException e) {
            logger.error("initListenSoldRecord连接失败：" + e.getMessage());
            logger.error("尝试重连。。。。：");
        }
    }

    private DefaultBlockParameter getStartBlockNumber() {
        return DefaultBlockParameter.valueOf(BigInteger.valueOf(0));
    }

    /**
     * 创建socket连接到合约
     *
     * @param ip   合约IP地址
     * @param port 合约端口
     * @return 返回
     */
    private WebSocketService getWebSocketService(String ip, String port) {
        try {
            WebSocketClient mWebSocketClient = new WebSocketClient(new URI("ws://" + EthUtils.IP + ":" + EthUtils.PORT));
            return new WebSocketService(mWebSocketClient, true);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return new WebSocketService("ws://" + ip + ":" + port, true);
    }

    /**
     * 最后一近区块高度 如果没有则返回default
     * 每次获取到区块存入redis
     *
     * @param key redisKey
     * @return 返回区块高度
     */
    public static DefaultBlockParameter getStartBlockNumber(String key) {
        return DefaultBlockParameterName.EARLIEST;
    }

    /**
     * 初始化Filter
     *
     * @param startBlock 开始区块
     * @param endBlock   结束区块
     * @param address    合约地址
     * @return 返回
     */
    public static EthFilter getFilter(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock, String address) {
        return new EthFilter(
                startBlock, endBlock, address);
    }

    /**
     * 初始化 minted事件参数
     *
     * @return 返回
     */

    public static Event getMintedEvent() {
        return new Event(
                EventType.MINTED,
                Arrays.asList(
                        new TypeReference<Address>(true) {
                        },
                        new TypeReference<Uint256>(true) {
                        },
                        new TypeReference<Bytes32>(true) {
                        }
                ));
    }
    public static Event minerEvent() {
        return new Event(
                "getMinerInfoByMinerId",
                Arrays.<TypeReference<?>>asList(
                        new TypeReference<Bytes32>(true) {
                        }
                ));
    }

    public static Event getOriHashSetEvent() {
        return new Event(
                "oriHashSet",
                Arrays.asList(
                        new TypeReference<Address>(true) {
                        },
                        new TypeReference<Uint256>(true) {
                        },
                        new TypeReference<Bytes32>(true) {
                        }
                ));
    }

    /**
     * 初始化 statusChange事件参数
     *
     * @return 返回
     */
    public static Event getStatusChangeEvent() {
        return new Event(
                EventType.STATUS_CHANGE,
                Arrays.<TypeReference<?>>asList(
                        new TypeReference<Uint256>(true) {
                        },
                        new TypeReference<Uint8>(true) {
                        }
                )
        );
    }

    public static Event setMinerEvt() {
        return new Event(
                "setMinerEvt",
                Arrays.<TypeReference<?>>asList(
                        new TypeReference<Bytes32>(true) {
                        },
                        new TypeReference<Address>(true) {
                        },
                        new TypeReference<Utf8String>(true) {
                        }
                )
        );
    }
    public static Event setMinerInfoEvt() {
        return new Event(
                "MinerInfo",
                Arrays.<TypeReference<?>>asList(
                        new TypeReference<Bytes32>(true) {
                        }
                )
        );
    }



    public static Event getSoldRecordEvent() {
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
                        new TypeReference<Uint256>(true) {
                        }
                )
        );
    }


    public static Event getSoldRecordEvent1() {
        return new Event(
                "NFTRecord",
                Arrays.<TypeReference<?>>asList(
                        new TypeReference<Utf8String>(true) {
                        },
                        new TypeReference<Uint256>(true) {
                        },
                        new TypeReference<Uint256>(true) {
                        },
                        new TypeReference<Uint256>(true) {
                        },
                        new TypeReference<Uint256>(true) {
                        }
                )
        );
    }

    public static Event getRegisterDappContractAddrEvt() {
        return new Event(
                "registerDappContractAddrEvt",
                Arrays.<TypeReference<?>>asList(
                        new TypeReference<Bytes32>(true) {
                        },
                        new TypeReference<Address>(true) {
                        },
                        new TypeReference<Address>(true) {
                        }
                )
        );
    }


    public static Event getTestString() {
        return new Event(
                "mutiStringFormat", Arrays.asList(
                new TypeReference<Uint256>(true) {
                },
                new TypeReference<Utf8String>(true) {
                },
                new TypeReference<Uint256>(true) {
                }
        )
        );
    }

//    public static Event getTestString() {
//        return new Event(
//                "mutiStringFormat", Arrays.asList(
//                new TypeReference<Utf8String>(true) {
//                },
//                new TypeReference<Utf8String>(true) {
//                },
//                new TypeReference<Utf8String>(true) {
//                },
//                new TypeReference<Uint256>(true) {
//                },
//                new TypeReference<Utf8String>(true) {
//                },
//                new TypeReference<Utf8String>(true) {
//                }
//        )
//        );
//    }


    public static Event getNewOrderEvent1() {
        return new Event(
                "newOrder", Arrays.asList(
                new TypeReference<Uint8>(true) {
                },
                new TypeReference<Uint256>(true) {
                },
                new TypeReference<Uint256>(true) {
                },
                new TypeReference<Uint256>(true) {
                },
                new TypeReference<Uint8>(true) {
                },
//                新增交易Hash参数 2022-05-24 lp
                new TypeReference<Utf8String>(true) {
                }
        )
        );
    }

    /**
     * 初始化 Transfer事件参数
     *
     * @return 返回
     */
    public Event getTransferEvent() {
        return new Event(
                EventType.TRANSFER,
                Arrays.<TypeReference<?>>asList(
                        new TypeReference<Address>(true) {
                        },
                        new TypeReference<Address>(true) {
                        },
                        new TypeReference<Uint256>(true) {
                        }
                ));
    }


    /**
     * 事件名称
     */
    public interface EventType {
        String MINTED = "minted";
        String SOLD_RECORD = "soldRecord";
        String TRANSFER = "Transfer";
        String STATUS_CHANGE = "statusChange";
        String NEW_ORDER = "newOrder";
        /**
         * soldRecord事件名称在数据库中改成sale
         */
        String SALE = "sale";
    }


    public static void main(String[] args) {
        int i = EthUtils.hexToInt("0x0000000000000000000000000000000000000000000000000000000000000006");
        System.out.println(i);
    }
}

class NelaMiner{

    private String minerId;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public String getMinerId() {
        return minerId;
    }

    public void setMinerId(String minerId) {
        this.minerId = minerId;
    }
}
