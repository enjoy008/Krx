package com.wearapay.krx.internal.observable;

import com.wearapay.krx.KObserver;

/**
 * Created by Kindy on 2017/9/22.
 */
public class KObservableEmpty<T> extends KObservableOrigin<T> {

  @Override protected void launch(KObserver<? super T> observer) {
    observer.onSubscribe(getToolkit().getDisposable());
    observer.onComplete();
  }
}
