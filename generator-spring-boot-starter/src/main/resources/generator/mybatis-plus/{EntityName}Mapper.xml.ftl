<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packagePath}.mapper.${entityName}Mapper">

    <!-- 通用查询映射结果 -->
    <resultMap id="BaseResultMap" type="${packagePath}.entity.${entityName}">
<#list fields as field>
        <result column="${field.columnName}" property="${field.propertyName}" />
</#list>
    </resultMap>

    <!-- 通用查询结果列 -->
    <sql id="BaseColumn">
<#list fields as field>
    <#if field_has_next>
        t.${field.columnName},
    <#else >
        t.${field.columnName}
    </#if>
</#list>
    </sql>

    <!-- 通用 Where 条件列 -->
    <sql id="BaseWhere">
        <where>
<#list fields as field>
    <#if field.propertyType == 'String'>
            <if test="p.${field.propertyName} != null and p.${field.propertyName} != '' ">
                and t.${field.columnName} = <#noparse>#{</#noparse>p.${field.propertyName}<#noparse>}</#noparse>
            </if>
    <#else>
            <if test="p.${field.propertyName} != null" >
                and t.${field.columnName} = <#noparse>#{</#noparse>p.${field.propertyName}<#noparse>}</#noparse>
            </if>
    </#if>
</#list>
        </where>
    </sql>

        <!-- 通用 Where Like 条件列 -->
        <sql id="BaseWhereLike">
            <where>
    <#list fields as field>
        <#if field.propertyType == 'String'>
                <if test="p.${field.propertyName} != null and p.${field.propertyName} != '' ">
                    and t.${field.columnName} like concat(concat('%', <#noparse>#{</#noparse>p.${field.propertyName}<#noparse>}</#noparse>), '%')
                </if>
        <#else>
                <if test="p.${field.propertyName} != null" >
                    and t.${field.columnName} = <#noparse>#{</#noparse>p.${field.propertyName}<#noparse>}</#noparse>
                </if>
        </#if>
    </#list>
            </where>
        </sql>

    <select id="page" resultType="${packagePath}.dto.${entityName}DTO">
        select
          <include refid="BaseColumn"/>
        from ${name} t
        <include refid="BaseWhereLike"/>
    </select>

</mapper>