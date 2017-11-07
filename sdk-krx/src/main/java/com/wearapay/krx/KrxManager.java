package com.wearapay.krx;

import com.wearapay.krx.internal.utils.KrxLog;
import com.wearapay.krx.internal.utils.KrxToolkit;

/**
 * Created by Kindy on 2017/9/20.
 */
public enum KrxManager {
  instance;

  public static KrxManager getInstance() {
    return instance;
  }

  private KrxToolkit toolkit;

  public void setDebug(boolean debug) {
    KrxLog.setDebug(debug);
  }

  public void executeTask(KScheduler scheduler, Runnable runnable) {
    toolkit.executeTask(scheduler, runnable);
  }

  KrxManager() {
    toolkit = new KrxToolkit();
  }
}
