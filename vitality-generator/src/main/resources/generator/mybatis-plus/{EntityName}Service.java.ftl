package ${packagePath}.service;

import ${packagePath}.mapper.${entityName}Mapper;
import ${packagePath}.entity.${entityName};
import com.baomidou.mybatisplus.core.metadata.IPage;
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

    public ${entityName}DTO detailById(Long id) {
        return ${entityName?uncap_first}Mapper.detailById(id);
    }

    public IPage<${entityName}DTO> page(IPage<${entityName}DTO> page, ${entityName}DTO dto){
        return ${entityName?uncap_first}Mapper.page(page, dto);
    }
}
