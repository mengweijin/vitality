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

    <!-- 通用 Equal 查询条件列 -->
    <sql id="BaseQueryConditionEqual">
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
    </sql>

        <!-- 通用 Like 查询条件列 -->
        <sql id="BaseQueryConditionLike">
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
        </sql>

    <select id="detailById" resultType="${packagePath}.dto.${entityName}DTO">
        select
            <include refid="BaseColumn"/>,
            cu.NICKNAME as create_by_name,
            uu.NICKNAME as update_by_name
        from ${name} t
        left join VTL_USER cu on cu.ID = t.CREATE_BY
        left join VTL_USER uu on uu.ID = t.UPDATE_BY
        where t.ID = <#noparse>#{</#noparse>id<#noparse>}</#noparse>
    </select>

    <select id="page" resultType="${packagePath}.dto.${entityName}DTO">
        select
            <include refid="BaseColumn"/>,
            cu.NICKNAME as create_by_name,
            uu.NICKNAME as update_by_name
        from ${name} t
        left join VTL_USER cu on cu.ID = t.CREATE_BY
        left join VTL_USER uu on uu.ID = t.UPDATE_BY
        <where>
            <include refid="BaseQueryConditionLike"/>
        </where>
        order by t.CREATE_TIME desc
    </select>

</mapper>
