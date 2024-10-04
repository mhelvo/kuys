package ${packageName};

import lombok.RequiredArgsConstructor;

import javax.inject.Named;

@Named
@RequiredArgsConstructor
public class ${simpleClassName} {
private final ${serviceClassName} service;

<#list methods as method>
    public ${method.return.type} ${method.name}(
    <#list method.parameters as parameter>
        final ${parameter.type} ${parameter.name}<#if parameter?has_next>,<#else>){</#if>
    </#list>
    <#if ! (method.return.isVoid?exists)>return </#if>
    <#if method.return.isBusinessEntity?exists>
        ${method.return.toDtoMapper}(
    </#if>
        service.${method.name}(
        <#list method.parameters as parameter>
           <#if parameter.isBusinessEntity?exists>${parameter.toMapper}(</#if>${parameter.name} <#if parameter.isBusinessEntity?exists>)</#if><#if parameter?has_next>,</#if>
        </#list>
       <#if method.return.isBusinessEntity?exists>)</#if>);
    }

</#list>
}
