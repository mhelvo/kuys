package nl.spindltree.util;

import lombok.experimental.UtilityClass;

import javax.lang.model.element.VariableElement;

@UtilityClass
public class ClassHelper {


    public static String packageName(String className) {
        String pName = null;
        int lastDot = className.lastIndexOf('.');
        if (lastDot > 0) {
            pName = className.substring(0, lastDot);
        }
        return pName;
    }


    public static String simpleClassName(String className) {
        String packageName = packageName(className);
        return className.substring(packageName.length() + 1);
    }

    public static String generateGetterMethodName(VariableElement variableElement) {
        String fieldName = getFieldName(variableElement);
        boolean isBoolean = variableElement.asType().toString().equals("boolean");
        String getterName;
        if (isBoolean && fieldName.startsWith("is")) {
            getterName = fieldName;
        } else {
            getterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        }

        return getterName;
    }

    public static String generateSetterMethodName(VariableElement variableElement) {
        String fieldName = getFieldName(variableElement);
        String setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        return setterName;
    }


    private static String getFieldName(VariableElement variableElement) {
        if (variableElement == null) {
            throw new IllegalArgumentException("VariableElement mag niet leeg zijn");
        }
        String fieldName = variableElement.getSimpleName().toString();
        return fieldName;
    }

}
