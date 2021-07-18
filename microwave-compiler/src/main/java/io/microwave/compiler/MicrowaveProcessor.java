package io.microwave.compiler;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ExecutableType;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes(value = {"io.microwave.annotation.ExportService"})
@SupportedSourceVersion(value = SourceVersion.RELEASE_8)
public class MicrowaveProcessor extends AbstractProcessor {
    private Logger log = LoggerFactory.getLogger(MicrowaveProcessor.class);
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
        log.info("开始处理注解类ExportService:{}", annotatedClass);
        if(!annotationElement.toString().equals("io.microwave.annotation.ExportService")) {
            return;
        }
        log.info("开始处理注解类ExportService:{}", annotatedClass);

        List<? extends Element>  allElements = annotatedClass.getEnclosedElements();

//        for(Element element: allElements) {
//            List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();
//            if(CollectionUtils.isEmpty(annotationMirrors)) {
//                continue;
//            }
//            for(AnnotationMirror annotationMirror: annotationMirrors) {
////                if(ExportService.class.getName()
////                        .equals(annotationMirror.getAnnotationType().toString())) {
////                    handleAnnotatedFactoryMethod(element);
////                }
//            }
//        }


    }

    /**
     * 开始处理标注@Factory的方法
     *
     */
    private void handleAnnotatedFactoryMethod(Element element) {
        log.info("开始处理标注@Factory的方法:{}", element.asType());
        ((ExecutableType)element.asType()).getReturnType();
        ExecutableType et = ((ExecutableType)element.asType());
        log.info("############:{}", et.getParameterTypes().get(0).toString());

        List<? extends Element> allElements = element.getEnclosedElements();
    }

}
