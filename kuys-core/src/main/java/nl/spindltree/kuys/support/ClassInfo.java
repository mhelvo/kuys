package nl.spindltree.kuys.support;

import lombok.Getter;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static nl.spindltree.kuys.support.ClassHelper.*;

@Getter
public class ClassInfo {
    private final String className;
    private final String simpleClassName;
    private final String packageName;

    private Map<String, Object> classInfoMap;

    public ClassInfo(TypeElement classElement, String Suffix) {


        TypeMirror classType = classElement.asType();

        className = classType.toString() + Suffix;
        packageName = packageName(className);
        simpleClassName = className.substring(packageName.length() + 1);

        String baseClassName = classType.toString();
        String baseSimpleClassName = baseClassName.substring(packageName.length() + 1);
        createMap(classElement, baseClassName, baseSimpleClassName);
    }

    private void createMap(TypeElement classElement, String baseClassName, String baseSimpleClassName) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("className", className);
        map.put("packageName", packageName);
        map.put("simpleClassName", simpleClassName);
        map.put("baseClassName", baseClassName);
        map.put("baseSimpleClassName", baseSimpleClassName);
        map.put("fields", createFieldMap(classElement));
        this.classInfoMap = Collections.unmodifiableMap(map);
    }

    private Object createFieldMap(TypeElement classElement) {
        return classElement.getEnclosedElements().stream()
                .filter(element -> ElementKind.FIELD == element.getKind())
                .map(VariableElement.class::cast)
                .map(element -> Map.of(
                        "fieldName", element.getSimpleName().toString(),
                        "fieldType", element.asType().toString(),
                        "getter", generateGetterMethodName(element),
                        "setter", generateSetterMethodName(element)
                ))
                .toList();
    }

    public Map<String, Object> toMap() {
        return classInfoMap;
    }
}
