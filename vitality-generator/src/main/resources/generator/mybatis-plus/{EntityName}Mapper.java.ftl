package ${packagePath}.mapper;

import ${packagePath}.entity.${entityName}DO;
import ${packagePath}.dto.${entityName}DTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * ${comment!} Mapper 接口
 *
 * @author ${author}
 * @since ${date}
 */
@Mapper
public interface ${entityName}Mapper extends BaseMapper<${entityName}DO> {

    /**
     * Get ${entityName} detail by id
     * @param id id
     */
    ${entityName}DTO detailById(Long id);

    /**
     * 自定义分页
     * @param page page
     * @param dto ${entityName}DTO
     * @return IPage
     */
    IPage<${entityName}DTO> page(IPage<${entityName}DTO> page, @Param("p") ${entityName}DTO dto);

}
