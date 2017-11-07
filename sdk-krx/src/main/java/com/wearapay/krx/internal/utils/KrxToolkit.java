package com.wearapay.krx.internal.utils;

import android.os.Handler;
import android.os.Looper;
import com.wearapay.krx.KDisposable;
import com.wearapay.krx.KScheduler;
import com.wearapay.krx.internal.disposable.KrxDisposable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Kindy on 2017/9/22.
 */
public final class KrxToolkit {
  private KDisposable disposable;
  private AtomicBoolean isShutdown;

  private Handler handler;
  private ExecutorService executorService;

  public KrxToolkit() {
    this.disposable = new KrxDisposable();
    isShutdown = new AtomicBoolean(false);
  }

  public KDisposable getDisposable() {
    return disposable;
  }

  public void executeTask(KScheduler scheduler, Runnable runnable) {
    KrxLog.o(this, "executeTask ", scheduler);
    if (isShutdown.get()) {
      KrxLog.e(this, "executeTask ignore, has shutdown.");
      return;
    }
    if (disposable != null && disposable.isDisposed()) {
      KrxLog.e(this, "executeTask ignore, has disposed.");
      return;
    }
    if (scheduler == KScheduler.ANDROID) {
      createHandler();
      handler.post(runnable);
    } else if (scheduler == KScheduler.IO) {
      createTaskPool();
      executorService.execute(runnable);
    } else {
      runnable.run();
    }
  }

  public void shutdownTasks() {
    if (isShutdown.get()) {
      return;
    }
    isShutdown.set(true);
    if (executorService != null) {
      executorService.shutdown();
    }
    if (handler != null) {
      handler.removeCallbacksAndMessages(null);
    }
  }

  private void createTaskPool() {
    if (executorService == null) {
      synchronized (this) {
        if (executorService == null) {
          executorService = Executors.newSingleThreadExecutor();
        }
      }
    }
  }

  private void createHandler() {
    if (handler == null) {
      synchronized (this) {
        if (handler == null) {
          handler = new Handler(Looper.getMainLooper());
        }
      }
    }
  }
}
