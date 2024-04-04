package nl.spindltree.annotation;

import com.google.auto.service.AutoService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SupportedAnnotationTypes("nl.spindltree.kuis.DTO")
@AutoService(Processor.class)
public class DTOProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {

            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);

            Element first = annotatedElements.stream().findFirst().get();

            TypeElement classElement = (TypeElement) first;

            // Haal het TypeMirror van de klasse op
            TypeMirror classType = classElement.asType();

            // Loop door alle velden van de klasse
            for (Element enclosedElement : classElement.getEnclosedElements()) {
                if (enclosedElement.getKind() == ElementKind.FIELD) {
                    VariableElement fieldElement = (VariableElement) enclosedElement;
                    String fieldName = fieldElement.getSimpleName().toString();
                    TypeMirror fieldType = fieldElement.asType();

                    // Hier kun je verdere verwerking uitvoeren op het veld
                    // bijvoorbeeld het ophalen van zijn annotaties, modifiers, etc.

                    // Print het veldnaam en het type
                    System.out.println("Field Name: " + fieldName);
                    System.out.println("Field Type: " + fieldType);
                }
            }
            List<VariableElement> fields = classElement.getEnclosedElements().stream()
                    .filter(element -> ElementKind.FIELD == element.getKind())
                    .map(VariableElement.class::cast)
                    .toList();

            try {
                writeDtoFile(classType.toString(), fields);
                writeDtoMapperFile(classElement);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }


        }

        return true;
    }

    private void writeDtoMapperFile(TypeElement classElement) throws Exception {
        TypeMirror classType = classElement.asType();
        String className = classType.toString() + "DtoMapper";

        Map<String, Object> input = new HashMap<String, Object>();
        input.put("className", className);
        input.put("className", className);
        String packageName = null;
        int lastDot = className.lastIndexOf('.');
        if (lastDot > 0) {
            packageName = className.substring(0, lastDot);
        }
        String simpleClassName = className.substring(lastDot + 1);

        input.put("packageName", packageName);
        input.put("simpleClassName", simpleClassName);

//        input.put("exampleObject", new ValueExampleObject("Java object", "me"));
//
//        List<ValueExampleObject> systems = new ArrayList<ValueExampleObject>();
//        systems.add(new ValueExampleObject("Android", "Google"));
//        systems.add(new ValueExampleObject("iOS States", "Apple"));
//        systems.add(new ValueExampleObject("Ubuntu", "Canonical"));
//        systems.add(new ValueExampleObject("Windows7", "Microsoft"));
//        input.put("systems", systems);


        JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(className);
        Configuration cfg = new Configuration();

        // Where do we load the templates from:
        cfg.setClassForTemplateLoading(DTOProcessor.class, "/");

        // Some other recommended settings:
        cfg.setIncompatibleImprovements(new Version(2, 3, 20));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);


        // 2.2. Get the template

        Template template = cfg.getTemplate("nl/spindltree/annotation/DtoMapper.ftl");

        // 2.3. Generate the output

        // Write output to the console
        Writer consoleWriter = new OutputStreamWriter(System.out);
        template.process(input, consoleWriter);

        // For the sake of example, also write output into a file:
        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
            template.process(input, out);
        }
    }

    private void writeDtoFile(String className, List<VariableElement> fields) throws IOException {

        String packageName = null;
        int lastDot = className.lastIndexOf('.');
        if (lastDot > 0) {
            packageName = className.substring(0, lastDot);
        }

        String simpleClassName = className.substring(lastDot + 1);
        String dtoClassName = className + "Dto";
        String dtoSimpleClassName = dtoClassName.substring(lastDot + 1);

        JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(dtoClassName);
        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {

            if (packageName != null) {
                out.print("package ");
                out.print(packageName);
                out.println(";");
                out.println();
            }

            out.println("import lombok.Value;");
            out.println("@Value");

            out.print("public class ");
            out.print(dtoSimpleClassName);
            out.println(" {");
            out.println();

            fields.forEach(field -> {
                        String fieldName = field.getSimpleName().toString();
                        TypeMirror fieldType = field.asType();
                        out.print(fieldType);
                        out.print(" ");
                        out.print(fieldName);
                        out.println(";");
                    }
            );
            out.println("}");

        }
    }

}
