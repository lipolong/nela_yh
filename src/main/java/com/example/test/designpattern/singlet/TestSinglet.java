package com.example.test.designpattern.singlet;

import com.alibaba.fastjson.JSON;

/**
 * @author lp
 * @date 2021-12-31 16:33
 * 单例模式：（懒汉式-多线程）
 * 解决在高并发过程中，多个实例出现逻辑错误的情况。
 * 在特定的业务场景下避免对象重复创建，节约内存。
 */
public class TestSinglet {


    public static TestSinglet instance = null;


    public static TestSinglet instance(){
        if(instance == null ) {
            synchronized (TestSinglet.class) {
                if(instance == null) {
                    instance = new TestSinglet();
                }
            }
        }
        return instance ;
    }

    private String address;

    private String name;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
