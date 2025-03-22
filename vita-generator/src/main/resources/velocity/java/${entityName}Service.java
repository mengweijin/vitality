package ${package}.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import ${package}.domain.entity.${entityName};
import ${package}.mapper.${entityName}Mapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Service;
import java.util.Objects;

/**
 * $!{table.comment} ${entityName} Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 *
 * @author ${author}
 * @since ${date}
 */
@Slf4j
@Service
public class ${entityName}Service extends CrudRepository<${entityName}Mapper, ${entityName}> {

    /**
     * Custom paging query
     * @param page page
     * @param ${entityPropertyName} {@link ${entityName}}
     * @return IPage
     */
    public IPage<${entityName}> page(IPage<${entityName}> page, ${entityName} ${entityPropertyName}){
        LambdaQueryWrapper<${entityName}> query = new LambdaQueryWrapper<>();
        query
        #foreach($field in ${allFields})
            #set($upperFirstName = $hutoolStrUtil.upperFirst(${field.propertyName}))
            #if(${field.columnType}=="STRING")
                .eq(StrUtil.isNotBlank(${entityPropertyName}.get$upperFirstName()), ${entityName}::get$upperFirstName, ${entityPropertyName}.get$upperFirstName())#if(!$foreach.hasNext);#end
            #else
                .eq(!Objects.isNull(${entityPropertyName}.get$upperFirstName()), ${entityName}::get$upperFirstName, ${entityPropertyName}.get$upperFirstName())#if(!$foreach.hasNext);#end
            #end
        #end
        return this.page(page, query);
    }
}
