package com.wearapay.krx.internal.observable;

import com.wearapay.krx.KObservable;
import com.wearapay.krx.KScheduler;
import com.wearapay.krx.KrxManager;
import com.wearapay.krx.internal.utils.KrxToolkit;

/**
 * Created by Kindy on 2017/9/22.
 */
public abstract class KObservableBase<T> extends KObservable<T> {
  protected KrxToolkit toolkit;

  public KrxToolkit getToolkit() {
    if (toolkit == null) {
      setToolkit(findToolkit());
    }
    return toolkit;
  }

  public void setToolkit(KrxToolkit toolkit) {
    this.toolkit = toolkit;
  }

  protected void executeTask(KScheduler scheduler, Runnable runnable) {
    KrxToolkit toolkit = getToolkit();
    if (toolkit == null || toolkit.getDisposable().isDisposed()) {
      if (toolkit != null) {
        toolkit.shutdownTasks();
      }
      KrxManager.getInstance().executeTask(scheduler, runnable);
    } else {
      toolkit.executeTask(scheduler, runnable);
    }
  }

  protected abstract KrxToolkit findToolkit();
}
