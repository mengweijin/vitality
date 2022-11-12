package ${packagePath}.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

<#assign tableName='${name?lower_case}'>
/**
 * ${comment!} 前端控制器
 *
 * @author ${author}
 * @since ${date}
 */
@Controller
@RequestMapping(${entityName}Controller.PREFIX)
public class ${entityName}Controller extends BaseController {

    public static final String PREFIX = "/${tableName?replace('_','-')}";

    @Autowired
    private ${entityName}Service ${entityName?uncap_first}Service;

    @PostMapping
    @ResponseBody
    public void add(${entityName} ${entityName?uncap_first}) {
        ${entityName?uncap_first}Service.save(${entityName?uncap_first});
    }

    @PutMapping
    @ResponseBody
    public void edit(${entityName} ${entityName?uncap_first}) {
        ${entityName?uncap_first}Service.updateById(${entityName?uncap_first});
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void delete(@PathVariable("id") Long id) {
        ${entityName?uncap_first}Service.removeById(id);
    }

    @DeleteMapping
    @ResponseBody
    public void delete(Long[] ids) {
        ${entityName?uncap_first}Service.removeBatchByIds(Arrays.asList(ids));
    }

    @GetMapping("/page")
    @ResponseBody
    public IPage<${entityName}DTO> page(Page<${entityName}DTO> page, ${entityName}DTO dto) {
        return ${entityName?uncap_first}Service.selectPageVO(page, dto);
    }
}
