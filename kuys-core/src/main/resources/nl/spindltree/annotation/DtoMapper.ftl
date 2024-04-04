package ${packageName};

import lombok.experimental.UtilityClass;

@UtilityClass
public class ${simpleClassName} {

    public static ${dtoClassName} to${simpleDtoClassName}(${baseClassName} ${f.toVariable(baseSimpleClassName)}) {
        return new ${dtoClassName}(
                <#list fields as field>
                    ${f.toVariable(baseSimpleClassName)}.${field.getter}()<#sep>, </#sep>
                </#list>
                );

}

    public static ${baseClassName} to${baseSimpleClassName}(${dtoClassName} orderDto) {
        ${baseClassName} ${f.toVariable(baseSimpleClassName)} = new ${baseClassName}();
        <#list fields as field>
            ${f.toVariable(baseSimpleClassName)}.${field.setter}(${f.toVariable(simpleDtoClassName)}.${field.getter}());
        </#list>
        return ${f.toVariable(baseSimpleClassName)};
    }

}