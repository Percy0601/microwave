package io.microwave.compiler;

import io.microwave.compiler.util.ClassNameUtil;
import io.microwave.compiler.util.FreemarkerUtil;
import io.microwave.compiler.util.MetaHolder;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.util.Set;

@SupportedAnnotationTypes(value = {"io.microwave.annotation.MicrowaveServer"})
@SupportedSourceVersion(value = SourceVersion.RELEASE_8)
public class MicrowaveServerProcessor extends AbstractProcessor {
    private Logger log = LoggerFactory.getLogger(MicrowaveServerProcessor.class);
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotationElement: annotations) {
            Set<? extends Element> annotatedClasses = roundEnv.getElementsAnnotatedWith(annotationElement);
            for(Element annotatedClass: annotatedClasses) {
                handleAnnotationClass(annotationElement, annotatedClass);
            }
        }
        return true;
    }

    private void handleAnnotationClass(TypeElement annotationElement, Element annotatedClass) {
        log.info("开始处理注解类MicrowaveServerProcessor:{}", annotatedClass);
        if(!annotationElement.toString().equals("io.microwave.annotation.MicrowaveServer")) {
            return;
        }

        try {
            String className = annotatedClass.toString();
            String targetClassName = ClassNameUtil.getPackageName(className) +
                    "._" +
                    ClassNameUtil.getSimpleClassName(className);
            JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(targetClassName);
            FreemarkerUtil.handleServer(targetClassName, builderFile.openWriter());
        } catch (IOException e) {
            log.warn("MicrowaveServerProcessor handleAnnotationClass Exception:{}", ExceptionUtils.getStackTrace(e));
        }

    }

}
