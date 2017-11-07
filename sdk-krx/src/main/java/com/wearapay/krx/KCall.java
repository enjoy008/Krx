package com.wearapay.krx;

/**
 * Created by Kindy on 2017/9/29.
 */
public interface KCall<T> {
  T call() throws Exception;
}
