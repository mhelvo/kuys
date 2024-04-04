package ${packageName};

import lombok.Value;
@Value
public class ${simpleClassName}  {
    <#list fields as field>
        ${field.fieldType} ${field.fieldName};
    </#list>
}
