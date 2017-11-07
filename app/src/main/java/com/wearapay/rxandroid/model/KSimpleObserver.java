package com.wearapay.rxandroid.model;

import android.util.Log;
import com.wearapay.krx.KDisposable;
import com.wearapay.krx.KObserver;
import com.wearapay.krx.internal.annotation.NonNull;

/**
 * Created by Kindy on 2017/9/18.
 */
public class KSimpleObserver<T> implements KObserver<T> {
  @Override public void onSubscribe(@NonNull KDisposable disposable) {
    o("========================= onSubscribe", disposable);
  }

  @Override public void onNext(@NonNull T value) {
    o("========================= onNext", value);
  }

  @Override public void onError(@NonNull Throwable throwable) {
    o("========================= onError", throwable);
  }

  @Override public void onComplete() {
    o("========================= onComplete");
  }

  private void o(Object... logs) {
    StringBuilder sb = new StringBuilder();
    for (Object log : logs) {
      sb.append(log).append(" ");
    }
    Log.v(KSimpleObserver.class.getSimpleName(), sb.toString() + Thread.currentThread());
  }
}
