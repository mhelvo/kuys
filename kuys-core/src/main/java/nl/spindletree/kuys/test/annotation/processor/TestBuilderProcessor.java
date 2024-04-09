package nl.spindletree.kuys.test.annotation.processor;

import com.google.auto.service.AutoService;
import freemarker.template.Template;
import nl.spindletree.kuis.test.TestBuilder;
import nl.spindletree.kuys.support.ClassInfo;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static nl.spindletree.kuys.support.TemplateHelper.functionSet;
import static nl.spindletree.kuys.support.TemplateHelper.template;

@SupportedSourceVersion(SourceVersion.RELEASE_21)
@SupportedAnnotationTypes("nl.spindletree.kuis.test.TestBuilder")
@AutoService(Processor.class)
public class TestBuilderProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
            annotatedElements.stream()
                    .map(ExecutableElement.class::cast)
                    .forEach(this::generateForElement);
        }
        return true;
    }

    private void generateForElement(ExecutableElement element) {
        try {
            TestBuilder annotation = element.getAnnotation(TestBuilder.class);

            TypeMirror classValue = classValueTrick(annotation);
            TypeElement baseClass = processingEnv.getElementUtils().getTypeElement(classValue.toString());
            ClassInfo classInfo = new ClassInfo(baseClass, "TestBuilder");
            JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(classInfo.getClassName());
            Template template = template("nl/spindletree/annotation/test/TestBuilder.ftl");

            Map<String, Object> input = new HashMap<>(classInfo.toMap());
            input.put("contentFile", annotation.contentFile());
            input.put("f", functionSet());

            try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
                template.process(input, out);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private TypeMirror classValueTrick(TestBuilder annotation) {
        try {
            annotation.baseClass();
        } catch (MirroredTypeException mte) {
            return mte.getTypeMirror();
        }
        throw new IllegalStateException("Can not find type mirror of annotation");
    }
}
