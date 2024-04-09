package nl.spindltree.kuys.support;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
import lombok.experimental.UtilityClass;
import nl.spindltree.kuys.domain.annotation.processor.DTOProcessor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
        functionMap.put("toVariable", FunctionHelper.function(TemplateHelper::toVariableName));

        return functionMap;
    }

    private static String toVariableName(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        } else {
            char firstChar = Character.toLowerCase(input.charAt(0));
            return firstChar + input.substring(1);
        }
    }
}
