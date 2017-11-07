package com.wearapay.krx.internal.observer;

import com.wearapay.krx.KDisposable;
import com.wearapay.krx.KObserver;
import com.wearapay.krx.internal.annotation.NonNull;
import com.wearapay.krx.internal.utils.KrxLog;
import com.wearapay.krx.internal.utils.ObjectHelper;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Kindy on 2017/9/18.
 */
public abstract class KObserverBase<T, R> implements KObserver<T> {
  protected final KObserver<? super R> next;
  private KDisposable disposable;
  private AtomicBoolean done;

  public KObserverBase(KObserver<? super R> next) {
    this.next = next;
    done = new AtomicBoolean(false);
  }

  public KDisposable getDisposable() {
    return disposable;
  }

  protected void setDisposable(KDisposable disposable) {
    KrxLog.o(this, "setDisposable ", disposable);
    this.disposable = disposable;
  }

  private boolean isDisposed() {
    return disposable != null && disposable.isDisposed();
  }

  protected abstract void onShotNext(T value);

  protected void onShotSubscribe(KDisposable disposable) {
    next.onSubscribe(disposable);
  }

  protected void onShotError(Throwable throwable) {
    next.onError(throwable);
  }

  protected void onShotComplete() {
    next.onComplete();
  }

  @Override public void onSubscribe(@NonNull KDisposable disposable) {
    KrxLog.o(this, "onSubscribe ", disposable);
    disposable = ObjectHelper.requestNonNull(disposable, "The disposable returned a null value.");
    setDisposable(disposable);
    onShotSubscribe(disposable);
  }

  @Override public void onNext(@NonNull T value) {
    KrxLog.o(this, "onNext ", value);
    value = ObjectHelper.requestNonNull(value, "The value returned a null value.");
    if (done.get()) {
      KrxLog.e(this, "has done.");
      return;
    }
    if (isDisposed()) {
      KrxLog.e(this, disposable, " has disposed");
      return;
    }
    onShotNext(value);
  }

  @Override public void onError(@NonNull Throwable throwable) {
    KrxLog.e(this, "onError ", throwable);
    throwable = ObjectHelper.requestNonNull(throwable, "The throwable returned a null value.");
    if (done.get()) {
      KrxLog.e(this, "has done.");
      return;
    }
    if (isDisposed()) {
      KrxLog.e(this, disposable, " has disposed.");
      return;
    }
    onShotError(throwable);
    done.set(true);
  }

  @Override public void onComplete() {
    KrxLog.o(this, "onComplete");
    if (done.get()) {
      KrxLog.e(this, "has done.");
      return;
    }
    if (isDisposed()) {
      KrxLog.e(this, disposable, " has disposed.");
      return;
    }
    onShotComplete();
    done.set(true);
  }
}
