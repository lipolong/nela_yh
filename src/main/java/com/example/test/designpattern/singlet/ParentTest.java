package com.example.test.designpattern.singlet;

import com.alibaba.fastjson.JSON;

/**
 * @author lp
 * @date 2021-12-31 16:51
 */
public class ParentTest extends SingleTonParent{

    private String name;

    private String address;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
