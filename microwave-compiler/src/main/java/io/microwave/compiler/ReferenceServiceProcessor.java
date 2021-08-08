package io.microwave.compiler;

import com.alibaba.fastjson.JSON;
import io.microwave.annotation.Reference;
import io.microwave.compiler.model.MethodElement;
import io.microwave.compiler.model.ReferenceProcessorEntry;
import io.microwave.compiler.util.MetaHolder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        Reference reference = annotatedClass.getAnnotation(Reference.class);
        ExecutableElement executableElement = (ExecutableElement) annotatedClass;
        DeclaredType interfaceClass = (DeclaredType)executableElement.getReturnType();
        List<? extends Element> elements = interfaceClass.asElement().getEnclosedElements();
        if(elements.isEmpty()) {
            log.info("处理Reference, 类名称:{}中没有任何方法", interfaceClass);
            return;
        }
        List<MethodElement> methodElements = new ArrayList<>();
        for(Element ele: elements) {
            MethodElement method = new MethodElement();
            ExecutableElement ee = (ExecutableElement)ele;
            method.setName(ee.getSimpleName().toString());
            TypeMirror returnType = ee.getReturnType();
            method.setReturnType(returnType.toString());
            methodElements.add(method);
            List<? extends VariableElement> ves = ee.getParameters();
            if(ves.isEmpty()) {
                continue;
            }
            List<String> params = new ArrayList<>();
            for(VariableElement ve: ves) {
                params.add(ve.asType().toString());
            }
            method.setParamTypes(params);
        }

        ReferenceProcessorEntry entry = new ReferenceProcessorEntry();
        entry.setInterfaceName(interfaceClass.toString());
        entry.setIdleConnection(reference.idle());
        entry.setMinConnection(reference.min());
        entry.setMaxConnection(reference.max());
        entry.setReferApplication(reference.refer());
        entry.setLocalApplication(reference.local());
        entry.setMethodElements(methodElements);
        log.info("entry:{}", JSON.toJSONString(entry));
        MetaHolder.addReferService(interfaceClass.toString(), entry);
    }


}
