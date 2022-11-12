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
    <sql id="Base_Column_List">
<#list fields as field>
        t.${field.columnName},
</#list>
    </sql>

    <!-- 通用 Where 条件列 -->
    <sql id="Base_Where_List">
        <where>
<#list fields as field>
    <#if field.propertyType == 'String'>
            <if ${field.propertyName} != null and ${field.propertyName} != '' >
                and t.${field.columnName} = <#noparse>#{</#noparse>${field.propertyName}<#noparse>}</#noparse>
            </if>
    <#else>
            <if ${field.propertyName} != null >
                and t.${field.columnName} = <#noparse>#{</#noparse>${field.propertyName}<#noparse>}</#noparse>
            </if>
    </#if>
</#list>
        </where>
    </sql>

    <select id="selectPageVO" resultType="${packagePath}.dto.${entityName}DTO">
        select
          <include refid="Base_Column_List"/>
        from ${name} t
        <include refid="Base_Where_List"/>
    </select>

</mapper>
