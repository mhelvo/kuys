package nl.spindltree.annotation.processor;

import com.google.auto.service.AutoService;
import freemarker.template.Template;
import nl.spindltree.annotation.ClassInfo;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static nl.spindltree.util.ClassHelper.simpleClassName;
import static nl.spindltree.util.TemplateHelper.functionSet;
import static nl.spindltree.util.TemplateHelper.template;

@SupportedAnnotationTypes("nl.spindltree.kuis.Dto")
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
        Template template1 = template("nl/spindltree/annotation/DtoMapper.ftl");

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
        Template template = template("nl/spindltree/annotation/Dto.ftl");

        Map<String, Object> input = new HashMap<>(classInfo.toMap());

        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
            template.process(input, out);
        }
    }

}
