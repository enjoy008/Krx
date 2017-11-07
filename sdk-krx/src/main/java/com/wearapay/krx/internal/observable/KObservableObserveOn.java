package com.wearapay.krx.internal.observable;

import com.wearapay.krx.KObservable;
import com.wearapay.krx.KObserver;
import com.wearapay.krx.KScheduler;
import com.wearapay.krx.internal.observer.KObserverBase;

/**
 * Created by Kindy on 2017/9/19.
 */
public class KObservableObserveOn<T> extends KObservableLift<T> {
  private final KObservable<? super T> source;
  private final KScheduler scheduler;

  public KObservableObserveOn(KObservable<? super T> source, KScheduler scheduler) {
    this.source = source;
    this.scheduler = scheduler;
  }

  @Override protected KObservable findObservable() {
    return source;
  }

  @Override protected void launch(KObserver<? super T> observer) {
    source.subscribe(new KObserverObserveOn(observer, new KTaskListener() {
      @Override public void execute(Runnable runnable) {
        executeTask(scheduler, runnable);
      }
    }));
  }

  private static final class KObserverObserveOn<T> extends KObserverBase<T, T> {
    private final KTaskListener listener;

    public KObserverObserveOn(KObserver<? super T> next, KTaskListener listener) {
      super(next);
      this.listener = listener;
    }

    @Override protected void onShotNext(T value) {
      listener.execute(new KObserverOnNextTask<>(next, value));
    }

    @Override protected void onShotError(Throwable throwable) {
      listener.execute(new KObserverOnErrorTask<>(this, throwable));
    }

    @Override protected void onShotComplete() {
      listener.execute(new KObserverOnCompleteTask<>(this));
    }

    private void superOnShotError(Throwable throwable) {
      super.onShotError(throwable);
    }

    private void superOnShotComplete() {
      super.onShotComplete();
    }
  }

  private static class KObserverOnNextTask<T> implements Runnable {
    private final KObserver<? super T> next;
    private final T value;

    public KObserverOnNextTask(KObserver<? super T> next, T value) {
      this.next = next;
      this.value = value;
    }

    @Override public void run() {
      next.onNext(value);
    }
  }

  private static class KObserverOnErrorTask<T> implements Runnable {
    private final KObserverObserveOn<T> source;
    private final Throwable throwable;

    public KObserverOnErrorTask(KObserverObserveOn<T> source, Throwable throwable) {
      this.source = source;
      this.throwable = throwable;
    }

    @Override public void run() {
      source.superOnShotError(throwable);
    }
  }

  private static class KObserverOnCompleteTask<T> implements Runnable {
    private final KObserverObserveOn<T> source;

    public KObserverOnCompleteTask(KObserverObserveOn<T> source) {
      this.source = source;
    }

    @Override public void run() {
      source.superOnShotComplete();
    }
  }

  private interface KTaskListener {
    void execute(Runnable runnable);
  }
}
