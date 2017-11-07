package com.wearapay.rxandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.wearapay.krx.KCall;
import com.wearapay.krx.KDisposable;
import com.wearapay.krx.KFunction;
import com.wearapay.krx.KObservable;
import com.wearapay.krx.KScheduler;
import com.wearapay.krx.KrxManager;
import com.wearapay.krx.internal.KObservableSource;
import com.wearapay.krx.internal.annotation.NonNull;
import com.wearapay.rxandroid.model.KSimpleObserver;
import com.wearapay.rxandroid.model.Person;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //KrxManager.getInstance().setDebug(true);
  }

  public void onClick(View v) {
    //testSimpleK();
    testK();
  }

  private KDisposable kDisposable;

  public void onClickCancel(View v) {
    if (kDisposable != null) {
      kDisposable.dispose();
    }
  }

  private void testSimpleK() {
    String name = "Kindy";
    String[] names = new String[] { "Kindy", "Lucy", "Lily", "Leo" };
    KObservable.from(names)
        .observeOn(KScheduler.ANDROID)
        .map(new KFunction<String, Person>() {
          @Override public Person apply(@NonNull String value) throws Exception {
            return new Person(value, value.length());
          }
        })
        .observeOn(KScheduler.IO)
        //.map(new KFunction<Person, String>() {
        //  @Override public String apply(@NonNull Person value) throws Exception {
        //    Thread.sleep(1000L);
        //    return value.toString();
        //  }
        //})
        .flatMap(new KFunction<Person, KObservableSource<String>>() {
          @Override public KObservableSource<String> apply(@NonNull Person value) throws Exception {
            if ("Lily".equals(value.getName())) {
              //throw new NullPointerException("Found the person who is " + value);
            }
            String name = value.getName() + "__";
            String[] suffix = new String[] {
                name + "A", name + "B", name + "C", name + "D"
            };
            Thread.sleep(1000L);
            return KObservable.from(suffix);
          }
        })
        .subscribeOn(KScheduler.IO)
        .observeOn(KScheduler.ANDROID)
        .subscribe(new KSimpleObserver<String>() {
          @Override public void onSubscribe(@NonNull KDisposable disposable) {
            super.onSubscribe(disposable);
            kDisposable = disposable;
          }
        });
  }

  private void testK() {
    KObservable.create(new KCall<String>() {
      @Override public String call() throws Exception {
        return "Kindy";
      }
    })
        .map(new KFunction<String, Person>() {
          @Override public Person apply(String value) throws Exception {
            return new Person(value, value.length());
          }
        })
        .subscribeOn(KScheduler.IO)
        .observeOn(KScheduler.ANDROID)
        .subscribe(new KSimpleObserver<Person>());
  }
}
