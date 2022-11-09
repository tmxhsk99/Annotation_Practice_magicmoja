package org.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

//해당 애노테이셔능 어디까지 유지할 건지 (소스,클래스,런타임)
@Retention(RetentionPolicy.SOURCE)
public @interface Magic {
}
