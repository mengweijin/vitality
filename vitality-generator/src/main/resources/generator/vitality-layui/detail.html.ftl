<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" href="../../../component/pear/css/pear.css" />
</head>
<body>
<div id="descriptions"></div>
<script src="../../../component/layui/layui.js"></script>
<script src="../../../component/pear/pear.js"></script>
<script>
    layui.use(['descriptions'], function() {
        let desc = layui.descriptions;
        let url = layui.url();
        let id = url.search.id;

<#assign tableName='${name?lower_case}'>

        desc.render({
            elem: '#descriptions',
            fieldNum: 2,
            url: '/${tableName?replace('_','-')}/' + id,
            cols: [
<#list fields as field>
    <#if field.keyFlag>
                { field: '${field.propertyName}', title: '${field.columnName}', hide: true },
    <#else>
        <#if field.propertyType == 'LocalDateTime'>
                { field: '${field.propertyName}', title: '${field.columnName}', templet: function (d) {
                    return layui.util.toDateString(d.${field.propertyName},  "yyyy-MM-dd HH:mm:ss");
                }},
        <#elseif field.propertyType == 'LocalDate'>
                { field: '${field.propertyName}', title: '${field.columnName}', templet: function (d) {
                    return layui.util.toDateString(d.${field.propertyName},  "yyyy-MM-dd");
                }},
        <#elseif field.propertyType == 'LocalTime'>
                { field: '${field.propertyName}', title: '${field.columnName}', templet: function (d) {
                    return layui.util.toDateString(d.${field.propertyName},  "HH:mm:ss");
                }},
        <#else>
                { field: '${field.propertyName}', title: '${field.columnName}' },
        </#if>
    </#if>
</#list>
                { title: '描述', longtext: true, templet: function(d) {
                    return '';
                }},
                { field: 'createByName', title: '创建者', newline: true },
                { field: 'createTime', title: '创建时间', templet: function (d) {
                    return layui.util.toDateString(d.createTime, "yyyy-MM-dd HH:mm:ss");
                }},
                { field: 'updateByName', title: '更新者' },
                { field: 'updateTime', title: '更新时间', templet: function (d) {
                    return layui.util.toDateString(d.updateTime, "yyyy-MM-dd HH:mm:ss");
                }},
            ]
        });
    })
</script>
</body>
</html>
