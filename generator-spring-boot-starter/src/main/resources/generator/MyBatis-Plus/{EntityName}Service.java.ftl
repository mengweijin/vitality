package ${packagePath}.service;

import ${packagePath}.mapper.${entityName}Mapper;
import ${packagePath}.entity.${entityName};
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ${comment!} 服务类
 *
 * @author ${author}
 * @since ${date}
 */
@Service
public class ${entityName}Service extends ServiceImpl<${entityName}Mapper, ${entityName}> {

    @Autowired
    private ${entityName}Mapper ${entityName?uncap_first}Mapper;

    public IPage<${entityName}DTO> selectPageVO(IPage<${entityName}DTO> page, ${entityName}DTO dto){
        return ${entityName?uncap_first}Mapper.selectPageVO(page, dto);
    }
}
