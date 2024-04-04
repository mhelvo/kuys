package nl.spindltree.util;


import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;
import lombok.experimental.UtilityClass;

import java.util.function.Function;

@UtilityClass
public class FunctionHelper {

    public static TemplateMethodModelEx function(Function<String, String> function) {
        return arguments -> {
            if (arguments.size() != 1) {
                throw new TemplateModelException("The String method expects exactly one argument.");
            }
            TemplateScalarModel arg = (TemplateScalarModel) arguments.getFirst();
            String input = arg.getAsString();
            String result = function.apply(input);

            return new SimpleScalar(result);
        };
    }
}
