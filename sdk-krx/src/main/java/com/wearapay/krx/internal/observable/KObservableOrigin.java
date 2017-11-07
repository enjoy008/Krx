package com.wearapay.krx.internal.observable;

import com.wearapay.krx.internal.utils.KrxToolkit;

/**
 * Created by Kindy on 2017/9/22.
 */
public abstract class KObservableOrigin<T> extends KObservableBase<T> {

  @Override protected KrxToolkit findToolkit() {
    return new KrxToolkit();
  }
}
