package com.wearapay.krx.internal.utils;

/**
 * Created by Kindy on 2017/9/19.
 */
public final class ObjectHelper {

  public static boolean equals(Object o1, Object o2) {
    return o1 == o2 || o1 != null && o1.equals(o2);
  }

  public static  <T> T requestNonNull(T t, String message) {
    if (t == null) {
      throw new NullPointerException(message);
    }
    return t;
  }

  private ObjectHelper() {

  }
}
