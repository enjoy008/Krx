package com.wearapay.krx.internal.observable;

import com.wearapay.krx.KDisposable;
import com.wearapay.krx.KFunction;
import com.wearapay.krx.KObservable;
import com.wearapay.krx.KObserver;
import com.wearapay.krx.internal.KObservableSource;
import com.wearapay.krx.internal.observer.KObserverBase;
import com.wearapay.krx.internal.utils.KrxLog;
import com.wearapay.krx.internal.utils.KrxToolkit;
import com.wearapay.krx.internal.utils.ObjectHelper;

/**
 * Created by Kindy on 2017/9/19.
 */
public class KObservableFlatMap<T, R> extends KObservableLift<R> {
  private final KObservable<? super T> source;
  private final KFunction<? super T, ? extends KObservableSource<? extends R>> mapper;

  public KObservableFlatMap(KObservable<? super T> source,
      KFunction<? super T, ? extends KObservableSource<? extends R>> mapper) {
    this.source = source;
    this.mapper = mapper;
  }

  @Override protected KObservable findObservable() {
    return source;
  }

  @Override protected void launch(KObserver<? super R> observer) {
    source.subscribe(new KObserverFlatMap(observer, mapper, new ToolkitCapture() {
      @Override public KrxToolkit capture() {
        return getToolkit();
      }
    }));
  }

  private static final class KObserverFlatMap<T, R> extends KObserverBase<T, R> {
    private final KFunction<? super T, ? extends KObservableSource<? extends R>> mapper;
    private final ToolkitCapture capture;

    public KObserverFlatMap(KObserver<? super R> next,
        KFunction<? super T, ? extends KObservableSource<? extends R>> mapper,
        ToolkitCapture capture) {
      super(next);
      this.mapper = mapper;
      this.capture = capture;
    }

    @Override protected void onShotNext(T value) {
      value = ObjectHelper.requestNonNull(value, "The value returned a null value.");
      KObservableSource<? extends R> result;
      try {
        result = mapper.apply(value);
      } catch (Exception e) {
        onError(e);
        return;
      }
      result = ObjectHelper.requestNonNull(result,
          "The mapper function returned a null KObservableSource.");
      if (result instanceof KObservableBase) {
        ((KObservableBase) result).setToolkit(capture.capture());
      }
      result.subscribe(new KObserverFlapMapInner<>(next));
    }
  }

  private static final class KObserverFlapMapInner<R> extends KObserverBase<R, R> {

    public KObserverFlapMapInner(KObserver<? super R> next) {
      super(next);
    }

    @Override protected void onShotNext(R value) {
      next.onNext(value);
    }

    @Override protected void onShotSubscribe(KDisposable disposable) {
      // ignore onSubscribe
      //super.onShotSubscribe(disposable);
      if (next instanceof KObserverBase) {
        KDisposable sourceDisposable = ((KObserverBase) next).getDisposable();
        if (sourceDisposable != null && sourceDisposable != disposable) {
          KrxLog.o(this, "onSubscribe change form ", disposable, " to ", sourceDisposable);
          setDisposable(sourceDisposable);
        }
      }
    }

    @Override protected void onShotComplete() {
      // ignore onComplete
      //super.onShotComplete();
    }
  }

  private interface ToolkitCapture {
    KrxToolkit capture();
  }
}
