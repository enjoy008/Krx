package com.wearapay.krx;

import com.wearapay.krx.internal.annotation.NonNull;

/**
 * Created by Kindy on 2017/9/18.
 */
public interface KFunction<T, R> {
  @NonNull R apply(@NonNull T value) throws Exception;
}
