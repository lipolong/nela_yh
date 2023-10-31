package com.example.test.designpattern;

import com.example.test.designpattern.singlet.ParentTest;
import com.example.test.designpattern.singlet.SingleTonParent;

/**
 * @author lp
 * @date 2021-12-31 16:35
 */
public class mainPattern {

    public static void main(String[] args){
        //测试泛型单例模式
        ParentTest test = SingleTonParent.getSingleton(ParentTest.class);
        test.setAddress("sss");
        test.setName("sss");
        System.out.println(test);
        SingleTonParent.remove(ParentTest.class);
    }

}
