package ${packageName};

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ${simpleClassName}  {
    <#list fields as field>
        ${field.fieldType} ${field.fieldName};
    </#list>

    private ${simpleClassName}(${baseClassName} ${f.toVariable(baseSimpleClassName)}) {
        <#list fields as field>
            ${field.fieldName} = ${f.toVariable(baseSimpleClassName)}.${field.getter}();
        </#list>
    }

    public static ${simpleClassName} initialize() {
        return initialize(new ObjectMapper());
    }

    public static ${simpleClassName} initialize(ObjectMapper objectMapper) {
        try {
            return new ${simpleClassName}(
            objectMapper.readValue(
            Thread.currentThread().getContextClassLoader().getResource("${contentFile}"),
            ${baseClassName}.class));
        } catch (IOException e) {
            throw new IllegalStateException(
            "An exception occurred when trying to build a test builder: " + e.getMessage(),
            e);
        }
    }

    public ${baseClassName} build() {
        return new ${baseClassName}(
            <#list fields as field>
                ${field.fieldName}<#sep>, </#sep>
            </#list>
        );
    }

    <#list fields as field>
    public ${simpleClassName} ${field.fieldName}(${field.fieldType} ${field.fieldName}){
        this.${field.fieldName} = ${field.fieldName};
        return this;
    }

    </#list>
}
