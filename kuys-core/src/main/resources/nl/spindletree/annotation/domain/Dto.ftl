package ${packageName};

import lombok.AccessLevel;
import lombok.Value;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ${simpleClassName}  {
    <#list fields as field>
        ${field.fieldType} ${field.fieldName};
    </#list>
}
