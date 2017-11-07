package com.wearapay.krx.internal;

import com.wearapay.krx.KObserver;
import com.wearapay.krx.internal.annotation.NonNull;

/**
 * Created by Kindy on 2017/9/18.
 */
public interface KObservableSource<T> {
  void subscribe(@NonNull KObserver<? super T> observer);
}
