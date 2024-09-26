package ${packagePath}.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ${comment!}
 *
 * @author ${author}
 * @since ${date}
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("${name}")
public class ${entityName} extends BaseEntity {

<#list fields as field>
    <#if !field.entityIgnored>
        <#if field.comment!?length gt 0>
    /**
     * ${field.comment}
     */
        </#if>
        <#-- 主键 -->
        <#if field.keyFlag>
            <#if field.keyIdentityFlag>
    @TableId(value = "${field.columnName}", type = IdType.AUTO)
            <#else>
    @TableId(value = "${field.columnName}", type = IdType.ASSIGN_ID)
            </#if>
        <#else>
    @TableField("${field.columnName}")
        </#if>
        <#-- 日期、时间、Long -->
        <#if field.propertyType == 'LocalDateTime'>
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        <#elseif field.propertyType == 'LocalDate'>
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
        <#elseif field.propertyType == 'LocalTime'>
    @JsonFormat(pattern = "HH:mm:ss")
    @DateTimeFormat(pattern = "HH:mm:ss")
        <#elseif field.propertyType == 'Long'>
    @JsonSerialize(using = ToStringSerializer.class)
        </#if>
    private ${field.propertyType} ${field.propertyName};

    </#if>
</#list>
}
