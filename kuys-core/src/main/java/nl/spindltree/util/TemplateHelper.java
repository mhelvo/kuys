package nl.spindltree.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
import lombok.experimental.UtilityClass;
import nl.spindltree.annotation.processor.DTOProcessor;

import javax.lang.model.element.VariableElement;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static nl.spindltree.util.FunctionHelper.function;

@UtilityClass
public class TemplateHelper {

    public static Template template(String templateName) throws IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        cfg.setClassForTemplateLoading(DTOProcessor.class, "/");
        cfg.setIncompatibleImprovements(new Version(2, 3, 20));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        return cfg.getTemplate(templateName);
    }

    public static Map<String, Object> functionSet() {
        Map<String, Object> functionMap = new HashMap<>();
        functionMap.put("toVariable", function(TemplateHelper::toVariableName));

        return functionMap;
    }

    private static String toVariableName(String input) {
        if (input == null || input.isEmpty()) {
            return input; // Return input as is if it's null or empty
        } else {
            char firstChar = Character.toLowerCase(input.charAt(0)); // Convert the first character to lowercase
            return firstChar + input.substring(1); // Concatenate the modified first character with the rest of the string
        }
    }

    private static String generateGetterMethodName(VariableElement variableElement) {
        // Controleer of het VariableElement niet leeg is
        if (variableElement == null) {
            throw new IllegalArgumentException("VariableElement mag niet leeg zijn");
        }

        // Haal de naam van het veld op
        String fieldName = variableElement.getSimpleName().toString();

        // Bepaal of het veld een boolean is
        boolean isBoolean = variableElement.asType().toString().equals("boolean");

        // Genereer de getter-methode naam op basis van het veldtype
        String getterName;
        if (isBoolean && fieldName.startsWith("is")) {
            getterName = fieldName;
        } else {
            getterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        }

        return getterName;
    }


}
