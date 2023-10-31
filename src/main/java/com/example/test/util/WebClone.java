package com.example.test.util;


import io.reactivex.rxjava3.disposables.Disposable;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lp
 * @date 2022-01-12 11:20
 */
public class WebClone {

    public static ConcurrentHashMap<String, Disposable> map = new ConcurrentHashMap<>();
}
