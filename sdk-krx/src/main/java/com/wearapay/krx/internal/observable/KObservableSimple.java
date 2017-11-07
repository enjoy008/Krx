package com.wearapay.krx.internal.observable;

import com.wearapay.krx.KCall;
import com.wearapay.krx.KObserver;

/**
 * Created by Kindy on 2017/9/29.
 */
public class KObservableSimple<T> extends KObservableOrigin<T> {
  private final KCall<? extends T> call;

  public KObservableSimple(KCall<? extends T> call) {
    this.call = call;
  }

  @Override protected void launch(KObserver<? super T> observer) {
    observer.onSubscribe(getToolkit().getDisposable());
    try {
      observer.onNext(call.call());
    } catch (Exception e) {
      observer.onError(e);
      return;
    }
    observer.onComplete();
  }
}
