package org.anno;

import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.TYPE) //애노테이션을 적용시킬 클래스를 한정시킬 수 있음 (Interface, Class, Enum)
//해당 애노테이셔능 어디까지 유지할 건지 (소스,클래스,런타임)
@Retention(RetentionPolicy.SOURCE)
public @interface Magic {
}
