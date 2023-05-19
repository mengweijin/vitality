package ${packagePath}.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;

<#assign tableName='${name?lower_case}'>
/**
 * ${comment!} 控制器
 *
 * @author ${author}
 * @since ${date}
 */
@RestController
@RequestMapping("/${tableName?replace('_','-')}")
public class ${entityName}Controller extends BaseController {

    @Autowired
    private ${entityName}Service ${entityName?uncap_first}Service;

    @PostMapping
    public void add(${entityName} ${entityName?uncap_first}) {
        ${entityName?uncap_first}Service.save(${entityName?uncap_first});
    }

    @PutMapping
    public void edit(${entityName} ${entityName?uncap_first}) {
        ${entityName?uncap_first}Service.updateById(${entityName?uncap_first});
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        ${entityName?uncap_first}Service.removeById(id);
    }

    @DeleteMapping
    public void delete(Long[] ids) {
        ${entityName?uncap_first}Service.removeBatchByIds(Arrays.asList(ids));
    }

    @GetMapping("/{id}")
    public ${entityName} getById(@PathVariable("id") Long id) {
        return ${entityName?uncap_first}Service.getById(id);
    }

    @GetMapping("/page")
    public IPage<${entityName}DTO> page(Page<${entityName}DTO> page, ${entityName}DTO dto) {
        return ${entityName?uncap_first}Service.page(page, dto);
    }
}
