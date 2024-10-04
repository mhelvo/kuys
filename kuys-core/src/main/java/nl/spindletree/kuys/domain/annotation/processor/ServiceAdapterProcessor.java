package nl.spindletree.kuys.domain.annotation.processor;

import com.google.auto.service.AutoService;
import freemarker.template.Template;
import nl.spindletree.kuis.domain.Exposed;
import nl.spindletree.kuys.support.ClassInfo;
import nl.spindletree.kuys.support.MethodInfo;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static nl.spindletree.kuys.support.ClassHelper.simpleClassName;
import static nl.spindletree.kuys.support.TemplateHelper.functionSet;
import static nl.spindletree.kuys.support.TemplateHelper.template;

@SupportedSourceVersion(SourceVersion.RELEASE_21)
@SupportedAnnotationTypes("nl.spindletree.kuis.domain.BusinessService")
@AutoService(Processor.class)
public class ServiceAdapterProcessor extends AbstractProcessor {


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
            annotatedElements.stream()
                    .map(TypeElement.class::cast)
                    .forEach(this::generateForElement);
        }
        return true;
    }

    private void generateForElement(TypeElement classElement) {
        try {
            writeServiceAdapterFile(classElement);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private void writeServiceAdapterFile(TypeElement classElement) throws Exception {
        ClassInfo classInfo = new ClassInfo(classElement, "Adapter");
        JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(classInfo.getClassName());
        Template template = template("nl/spindletree/annotation/domain/ServiceAdapter.ftl");

//        Class<?> clazz = Class.forName(classInfo.getClassName());
//        Method[] methods = clazz.getDeclaredMethods();


        Map<String, Object> input = new HashMap<>(classInfo.toMap());
        String className = classElement.asType().toString() + "Adapter";
        input.put("className", className);
        input.put("serviceClassName", classElement.asType().toString());
        input.put("simpleDtoClassName", simpleClassName(className));
        input.put("methods", createMethodMap(classElement));
        input.put("f", functionSet());


        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
            template.process(input, out);
        }
    }


    private List<Map<String, Object>> createMethodMap(TypeElement classElement) {


        var bla = classElement.getEnclosedElements().stream()
                .filter(
                        element -> ElementKind.METHOD == element.getKind())
//                .map(ExecutableElement.class::cast)
                .findFirst();
        var bla2 = bla
                .map(b -> b.getAnnotationMirrors())
                .orElse(null);


        return classElement.getEnclosedElements().stream()
                .filter(element -> ElementKind.METHOD == element.getKind())
                .map(ExecutableElement.class::cast)
                .filter(element -> element.getAnnotation(Exposed.class) != null)
                .map(element -> new MethodInfo(element, this.processingEnv).toMap())
                .toList();

    }

}
