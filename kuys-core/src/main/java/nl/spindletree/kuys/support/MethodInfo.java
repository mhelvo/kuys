package nl.spindletree.kuys.support;

import nl.spindletree.kuis.domain.BusinessEntity;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.lang.model.type.TypeKind.DECLARED;
import static nl.spindletree.kuys.support.ClassHelper.packageName;
import static nl.spindletree.kuys.support.ClassHelper.simpleClassName;
import static nl.spindletree.kuys.support.Naming.DTO;
import static nl.spindletree.kuys.support.Naming.DTO_MAPPER;

public class MethodInfo {
    private final Map<String, Object> methodInfoMap = new HashMap<>();
//    private final ExecutableElement element;

    public MethodInfo(ExecutableElement element, ProcessingEnvironment processingEnv) {
        methodInfoMap.put("name", element.getSimpleName().toString());
        methodInfoMap.put("return", returnTypeInfo(element.getReturnType(), processingEnv));
        methodInfoMap.put("parameters", createParametersInfo(element.getParameters(), processingEnv));
    }

    private Map<String, Object> returnTypeInfo(TypeMirror mirror, ProcessingEnvironment processingEnv) {

        Element type = processingEnv.getTypeUtils().asElement(mirror);

        Map<String, Object> info = new HashMap<>();
        if (type == null) {
            info.put("isVoid", true);
            info.put("type", "void");
            return info;
        }

        Map<String, Object> infoClassType = classTypeInfoMap(type);

        info.putAll(infoClassType);
        return info;
    }

    private static Map<String, Object> classTypeInfoMap(Element type) {
        Map<String, Object> infoClassType = new HashMap<>();
        var annotation = type.getAnnotation(BusinessEntity.class);
        if (annotation == null) {
            infoClassType.put("type", type.toString());
        } else {
            var dtoClass = type.toString() + DTO;
            infoClassType.put("isBusinessEntity", true);
            infoClassType.put("type", dtoClass);

            String simpleClassName = simpleClassName(type.toString());
            String simpleClassNameDto = simpleClassName(dtoClass);
            String packageName = packageName(dtoClass);
            infoClassType.put("toDtoMapper", packageName + "." + simpleClassName + DTO_MAPPER + ".to" + simpleClassNameDto);
            infoClassType.put("toMapper", packageName + "." + simpleClassName + DTO_MAPPER + ".to" + simpleClassName);
        }
        return infoClassType;
    }

    private List<Map<String, Object>> createParametersInfo(List<? extends VariableElement> parameters, ProcessingEnvironment processingEnv) {
        return parameters.stream()
                .map(parameter -> createParamInfo(parameter, processingEnv))
                .toList();
    }

    private Map<String, Object> createParamInfo(VariableElement parameter, ProcessingEnvironment processingEnv) {
        Map<String, Object> info = new HashMap<>();
        info.put("name", parameter.getSimpleName());

        TypeMirror type = parameter.asType();
        if (type.getKind() == DECLARED) {

            info.putAll(classTypeInfoMap(
                    processingEnv.getTypeUtils()
                            .asElement(
                                    type)));


        } else {
            info.put("type", type.toString());
        }

        return info;
    }


    public Map<String, Object> toMap() {

        return methodInfoMap;
    }
}
