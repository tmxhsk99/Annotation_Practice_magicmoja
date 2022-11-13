package org.anno;

import com.google.auto.service.AutoService;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_11)
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
        //여기서는 처리하고 true를 리턴한다. 어떤경우는 여기서도 처리하고 , 다른 프로세서에게 마지막으로 처리할 수도 있다.

        // Magic이라는 애노테이션이 붙은 앨리맨트를 전부 가져온다.
        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(Magic.class);

        //만약에 해당 Magic 이란 애노테이션이 interface에만 붙이도록 하고 싶은 경우
        //애너테이션 자체의 @Target(ElementType.TYPE) 기능은 interface,Enum,Class 로 한정시키지 못하므로
        //프로세서에서 자체적으로 걸러야한다.
        for (Element element : elementsAnnotatedWith) {
            Name simpleName = element.getSimpleName();
            if (element.getKind() != ElementKind.INTERFACE) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Magic annotation can not be used on " + simpleName);
            } else {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,"Processing " + simpleName);
            }
        }
        return true;
    }
}
