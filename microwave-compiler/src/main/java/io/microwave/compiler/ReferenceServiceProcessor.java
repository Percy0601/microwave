package io.microwave.compiler;

import io.microwave.compiler.model.ReferenceProcessorEntry;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.lang.reflect.Method;
import java.util.Set;

@SupportedAnnotationTypes(value = {"io.microwave.annotation.Reference"})
@SupportedSourceVersion(value = SourceVersion.RELEASE_8)
public class ReferenceServiceProcessor extends AbstractProcessor {
    private Logger log = LoggerFactory.getLogger(ReferenceServiceProcessor.class);
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
        log.info("开始处理注解类Reference:{}", annotatedClass);
        if(!annotationElement.toString().equals("io.microwave.annotation.Reference")) {
            return;
        }
        ExecutableElement executableElement = (ExecutableElement) annotatedClass;
        TypeMirror interfaceClass = executableElement.getReturnType();
        try {
            Method[] methods = ClassUtils.getClass(interfaceClass.toString()).getMethods();
            if(methods.length < 1) {
                log.info("处理Reference, 类名称:{}中没有任何方法", interfaceClass.toString());
                return;
            }

            for(Method method: methods) {
                String className = ClassUtils.getName(method.getReturnType());


            }

        } catch (ClassNotFoundException e) {
            log.warn("处理Reference异常，ClassNotFoundException：{}", ExceptionUtils.getStackTrace(e));
        }
    }


}
