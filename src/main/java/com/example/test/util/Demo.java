package com.example.test.util;

/**
 * @author lp
 * @date 2021-11-22 14:48
 */
public class Demo {

    /**
     * 比特
     */
    static int MAX_BIT_LENGTH = 256;
    /**
     * 长度
     */
    static int MAX_BYTE_LENGTH = MAX_BIT_LENGTH / 8;
    /**
     * 偏移量
     */
    static final int MAX_BYTE_LENGTH_FOR_HEX_STRING = MAX_BYTE_LENGTH << 1;


    public static void main(String[] args) {
        int offset=0;
        for(int i=0;i<10;i++){
            offset+=MAX_BYTE_LENGTH_FOR_HEX_STRING;
            System.out.println("i="+i);
            System.out.println("offset="+offset);
        }






    }
}
