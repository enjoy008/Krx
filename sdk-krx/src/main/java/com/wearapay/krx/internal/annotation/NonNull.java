package com.wearapay.krx.internal.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Created by Kindy on 2017/9/25.
 */

@Documented @Retention(CLASS) @Target({ METHOD, PARAMETER, FIELD, ANNOTATION_TYPE, PACKAGE })
public @interface NonNull {
}
