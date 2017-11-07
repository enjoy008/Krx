package com.wearapay.krx.internal.disposable;

import com.wearapay.krx.KDisposable;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Kindy on 2017/9/22.
 */
public class KrxDisposable implements KDisposable {
  private AtomicBoolean disposed = new AtomicBoolean(false);

  @Override public void dispose() {
    disposed.set(true);
  }

  @Override public boolean isDisposed() {
    return disposed.get();
  }
}
