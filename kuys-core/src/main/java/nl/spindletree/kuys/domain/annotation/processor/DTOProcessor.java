package nl.spindletree.kuys.domain.annotation.processor;

import com.google.auto.service.AutoService;
import freemarker.template.Template;
import nl.spindletree.kuys.support.ClassInfo;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static nl.spindletree.kuys.support.ClassHelper.simpleClassName;
import static nl.spindletree.kuys.support.TemplateHelper.functionSet;
import static nl.spindletree.kuys.support.TemplateHelper.template;

@SupportedSourceVersion(SourceVersion.RELEASE_21)
@SupportedAnnotationTypes("nl.spindletree.kuis.domain.BusinessEntity")
@AutoService(Processor.class)
public class DTOProcessor extends AbstractProcessor {

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
            writeDtoFile(classElement);
            writeDtoMapperFile(classElement);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void writeDtoMapperFile(TypeElement classElement) throws Exception {
        ClassInfo classInfo = new ClassInfo(classElement, "DtoMapper");
        JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(classInfo.getClassName());
        Template template1 = template("nl/spindletree/annotation/domain/DtoMapper.ftl");

        Map<String, Object> input = new HashMap<>(classInfo.toMap());
        String dtoClassName = classElement.asType().toString() + "Dto";
        input.put("dtoClassName", dtoClassName);
        input.put("simpleDtoClassName", simpleClassName(dtoClassName));
        input.put("f", functionSet());

        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
            template1.process(input, out);
        }
    }

    private void writeDtoFile(TypeElement classElement) throws Exception {
        ClassInfo classInfo = new ClassInfo(classElement, "Dto");
        JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(classInfo.getClassName());
        Template template = template("nl/spindletree/annotation/domain/Dto.ftl");

        Map<String, Object> input = new HashMap<>(classInfo.toMap());

        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
            template.process(input, out);
        }
    }

}
