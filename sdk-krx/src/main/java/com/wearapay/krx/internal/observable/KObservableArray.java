package com.wearapay.krx.internal.observable;

import com.wearapay.krx.KDisposable;
import com.wearapay.krx.KObserver;
import com.wearapay.krx.internal.utils.KrxLog;

/**
 * Created by Kindy on 2017/9/19.
 */
public class KObservableArray<T> extends KObservableOrigin<T> {
  private final T[] values;

  public KObservableArray(T[] values) {
    this.values = values;
  }

  @Override protected void launch(KObserver<? super T> observer) {
    KDisposable disposable = getToolkit().getDisposable();
    observer.onSubscribe(disposable);
    for (T value : values) {
      if (disposable.isDisposed()) {
        KrxLog.o(this, "has disposed");
        return;
      }
      observer.onNext(value);
    }
    observer.onComplete();
  }
}
