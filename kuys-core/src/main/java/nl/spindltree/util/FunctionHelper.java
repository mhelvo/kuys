package nl.spindltree.util;


import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.function.Function;

@UtilityClass
public class FunctionHelper {

    public static TemplateMethodModelEx function(Function<String, String> function) {
        return new TemplateMethodModelEx() {
            @Override
            public Object exec(List arguments) throws TemplateModelException {
                if (arguments.size() != 1) {
                    throw new TemplateModelException("The String method expects exactly one argument.");
                }

                // Access the argument passed to the function
                TemplateScalarModel arg = (TemplateScalarModel) arguments.get(0);
                String input = arg.getAsString();

                // Perform some custom logic on the input
                String result = function.apply(input);

                return new SimpleScalar(result);
            }
        };
    }
}
