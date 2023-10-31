package com.example.test.listen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lp
 * @date 2022-05-05 15:42
 */
//@WebListener
public class MyApplicationListener implements ServletContextListener {

    private Logger logger = LoggerFactory.getLogger(MyApplicationListener.class);
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("liting: contextInitialized");
        System.err.println("初始化成功");
        ServletContext context = sce.getServletContext();
        // IP存储器
        Map<String, Long[]> ipMap = new HashMap<String, Long[]>(16);
        context.setAttribute("ipMap", ipMap);
        // 限制IP存储器：存储被限制的IP信息
        Map<String, Long> limitedIpMap = new HashMap<String, Long>(16);
        context.setAttribute("limitedIpMap", limitedIpMap);
        logger.info("ipmap："+ipMap.toString()+";limitedIpMap:"+limitedIpMap.toString()+"初始化成功。。。。。");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub
    }



}
