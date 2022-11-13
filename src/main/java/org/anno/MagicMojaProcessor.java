package org.anno;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import java.io.IOException;
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

        //제대로된 위치에 애너테이션을 적용했는 지 유효성을 검사한다.

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

            //소스 생성로직 해당 로직이 적용된 애너테이션은 다음과 같은 소스를 생성해낸다
            //javaPoet 라이브러리를 사용한다.
            TypeElement typeElement = (TypeElement) element;
            ClassName className = ClassName.get(typeElement);
            //우리는 pullOut이라는 메서드를 구현해야한다.
            MethodSpec pullOut = MethodSpec.methodBuilder("pullOut")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(String.class)
                    .addStatement("return $S", "Rabbit!")
                    .build();

            TypeSpec magicMoja = TypeSpec.classBuilder("MagicMoja")
                    .addModifiers(Modifier.PUBLIC)
                    .addSuperinterface(className)
                    .addMethod(pullOut)
                    .build();


            //위의 공정은 메모리상의 코드를 정의한것 실제 소스를 써야한다.

            Filer filer = processingEnv.getFiler();
            try {
                JavaFile.builder(className.packageName(), magicMoja)
                        .build()
                        .writeTo(filer);
            } catch (IOException e) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "FATAL ERROR " + e);
            }

        }




        return true;
    }
}
