package com.wearapay.krx.internal.observable;

import com.wearapay.krx.KFunction;
import com.wearapay.krx.KObservable;
import com.wearapay.krx.KObserver;
import com.wearapay.krx.internal.observer.KObserverBase;
import com.wearapay.krx.internal.utils.ObjectHelper;

/**
 * Created by Kindy on 2017/9/18.
 */
public class KObservableMap<T, R> extends KObservableLift<R> {
  private final KObservable<? super T> source;
  private final KFunction<? super T, ? extends R> mapper;

  public KObservableMap(KObservable<? super T> source, KFunction<? super T, ? extends R> mapper) {
    this.source = source;
    this.mapper = mapper;
  }

  @Override protected KObservable findObservable() {
    return source;
  }

  @Override protected void launch(KObserver<? super R> observer) {
    source.subscribe(new KObserverMap(observer, mapper));
  }

  private static final class KObserverMap<T, R> extends KObserverBase<T, R> {

    private final KFunction<? super T, ? extends R> mapper;

    public KObserverMap(KObserver<? super R> next, KFunction<? super T, ? extends R> mapper) {
      super(next);
      this.mapper = mapper;
    }

    @Override protected void onShotNext(T value) {
      value = ObjectHelper.requestNonNull(value, "The value returned a null value.");
      R result;
      try {
        result = mapper.apply(value);
      } catch (Exception e) {
        onError(e);
        return;
      }
      result = ObjectHelper.requestNonNull(result, "The mapper function returned a null value.");
      next.onNext(result);
    }
  }
}
