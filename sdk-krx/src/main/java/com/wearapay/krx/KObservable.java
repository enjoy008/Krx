package com.wearapay.krx;

import com.wearapay.krx.internal.KObservableSource;
import com.wearapay.krx.internal.annotation.NonNull;
import com.wearapay.krx.internal.observable.KObservableArray;
import com.wearapay.krx.internal.observable.KObservableEmpty;
import com.wearapay.krx.internal.observable.KObservableFlatMap;
import com.wearapay.krx.internal.observable.KObservableJust;
import com.wearapay.krx.internal.observable.KObservableMap;
import com.wearapay.krx.internal.observable.KObservableObserveOn;
import com.wearapay.krx.internal.observable.KObservableSimple;
import com.wearapay.krx.internal.observable.KObservableSubscribeOn;
import com.wearapay.krx.internal.utils.KrxLog;
import com.wearapay.krx.internal.utils.ObjectHelper;

/**
 * Created by Kindy on 2017/9/18.
 */
public abstract class KObservable<T> implements KObservableSource<T> {

  protected abstract void launch(KObserver<? super T> observer);

  @Override public void subscribe(@NonNull KObserver<? super T> observer) {
    KrxLog.o(this, "subscribe ", observer);
    observer = ObjectHelper.requestNonNull(observer, "The observer returned a null value.");
    launch(observer);
  }

  public <R> KObservable<R> map(KFunction<? super T, ? extends R> mapper) {
    mapper = ObjectHelper.requestNonNull(mapper, "The mapper function returned a null value.");
    return new KObservableMap<>(this, mapper);
  }

  public <R> KObservable<R> flatMap(
      KFunction<? super T, ? extends KObservableSource<? extends R>> mapper) {
    mapper = ObjectHelper.requestNonNull(mapper, "The mapper function returned a null value.");
    return new KObservableFlatMap<>(this, mapper);
  }

  public KObservable<T> subscribeOn(@NonNull KScheduler scheduler) {
    scheduler = ObjectHelper.requestNonNull(scheduler, "The scheduler returned a null value.");
    return new KObservableSubscribeOn<>(this, scheduler);
  }

  public KObservable<T> observeOn(KScheduler scheduler) {
    scheduler = ObjectHelper.requestNonNull(scheduler, "The scheduler returned a null value.");
    return new KObservableObserveOn<>(this, scheduler);
  }

  // ================== static factory method ==================

  public static <T> KObservable<T> create(@NonNull KCall<? extends T> call) {
    call = ObjectHelper.requestNonNull(call, "The call create returned a null value.");
    return new KObservableSimple<>(call);
  }

  public static <T> KObservable<T> from(@NonNull T... value) {
    value = ObjectHelper.requestNonNull(value, "The value from returned a null value.");
    if (value.length == 0) {
      return empty();
    } else if (value.length == 1) {
      return just(value[0]);
    }
    return array(value);
  }

  private static <T> KObservable<T> empty() {
    return new KObservableEmpty<>();
  }

  private static <T> KObservable<T> just(T value) {
    return new KObservableJust<>(value);
  }

  private static <T> KObservable<T> array(T[] values) {
    return new KObservableArray<>(values);
  }
}
