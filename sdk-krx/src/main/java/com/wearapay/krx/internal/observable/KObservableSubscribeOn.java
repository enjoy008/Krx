package com.wearapay.krx.internal.observable;

import com.wearapay.krx.KObservable;
import com.wearapay.krx.KObserver;
import com.wearapay.krx.KScheduler;
import com.wearapay.krx.internal.observer.KObserverBase;

/**
 * Created by Kindy on 2017/9/19.
 */
public class KObservableSubscribeOn<T> extends KObservableLift<T> {
  private final KObservable<? super T> source;
  private final KScheduler scheduler;

  public KObservableSubscribeOn(KObservable<? super T> source, KScheduler scheduler) {
    this.source = source;
    this.scheduler = scheduler;
  }

  @Override protected KObservable findObservable() {
    return source;
  }

  @Override protected void launch(KObserver<? super T> observer) {
    executeTask(scheduler, new KSubscribeOnTask<>(source, observer));
  }

  private static final class KObserverSubscribeOn<T> extends KObserverBase<T, T> {

    public KObserverSubscribeOn(KObserver<? super T> next) {
      super(next);
    }

    @Override protected void onShotNext(T value) {
      next.onNext(value);
    }
  }

  private static class KSubscribeOnTask<T> implements Runnable {
    private final KObservable<? super T> source;
    private final KObserver<? super T> observer;

    public KSubscribeOnTask(KObservable<? super T> source, KObserver<? super T> observer) {
      this.source = source;
      this.observer = observer;
    }

    @Override public void run() {
      source.subscribe(new KObserverSubscribeOn(observer));
    }
  }
}
