package org.anno;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Set;

public class MagicMojaProcessor extends AbstractProcessor {

    //이 프로세서가 어떤 애노테이션을 처리할 것인가 정도는 오버라이드 해준다.
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(Magic.class.getName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return super.getSupportedSourceVersion();
    }

    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //여기서 리턴을 true를 리턴하면 해당 애너테이션 프로세서는 처리했다는 의미이므로 다른 프로세서들이 처리하지 않게된다.
        //여기서는 처리하고 true를 리턴한다. 어떤경우는 여기서 도 처리하고 , 다른 프로세서에게 마지막으로 처리할 수도 있다.
        return true;
    }
}
