package com.wearapay.krx.internal.observable;

import com.wearapay.krx.KObservable;
import com.wearapay.krx.internal.utils.KrxLog;
import com.wearapay.krx.internal.utils.KrxToolkit;

/**
 * Created by Kindy on 2017/9/22.
 */
public abstract class KObservableLift<T> extends KObservableBase<T> {

  @Override protected KrxToolkit findToolkit() {
    KObservable observable = findObservable();
    if (observable instanceof KObservableBase) {
      return ((KObservableBase) observable).getToolkit();
    } else {
      KrxLog.e(this, observable, " is not instance of ", KObservableBase.class,
          "\nYou can create KObservable by KObservable.create() instead of ", observable);
    }
    return null;
  }

  protected abstract KObservable findObservable();
}
