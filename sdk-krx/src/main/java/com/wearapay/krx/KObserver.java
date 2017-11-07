package com.wearapay.krx;

import com.wearapay.krx.internal.annotation.NonNull;

/**
 * Created by Kindy on 2017/9/18.
 */
public interface KObserver<T> {
  void onSubscribe(@NonNull KDisposable disposable);

  void onNext(@NonNull T value);

  void onError(@NonNull Throwable throwable);

  void onComplete();
}
