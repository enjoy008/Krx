package com.wearapay.krx.internal.observable;

import com.wearapay.krx.KObserver;

/**
 * Created by Kindy on 2017/9/18.
 */
public class KObservableJust<T> extends KObservableOrigin<T> {
  private final T value;

  public KObservableJust(T value) {
    this.value = value;
  }

  @Override protected void launch(KObserver<? super T> observer) {
    observer.onSubscribe(getToolkit().getDisposable());
    observer.onNext(value);
    observer.onComplete();
  }
}
