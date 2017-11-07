package com.wearapay.krx.internal.utils;

import android.util.Log;

/**
 * Created by Kindy on 2017/9/21.
 */
public final class KrxLog {
  private static boolean debug = false;

  public static void setDebug(boolean debug) {
    KrxLog.debug = debug;
  }

  public static void o(Object o, Object... logs) {
    if (debug) {
      StringBuilder sb = new StringBuilder();
      for (Object log : logs) {
        sb.append(log);
      }
      sb.append(Thread.currentThread());
      Log.d(o.getClass().getSimpleName(), sb.toString());
    }
  }

  public static void e(Object o, Object... logs) {
    if (debug) {
      StringBuilder sb = new StringBuilder();
      for (Object log : logs) {
        sb.append(log);
      }
      sb.append(Thread.currentThread());
      Log.e(o.getClass().getSimpleName(), sb.toString());
    }
  }

  private KrxLog() {

  }
}
