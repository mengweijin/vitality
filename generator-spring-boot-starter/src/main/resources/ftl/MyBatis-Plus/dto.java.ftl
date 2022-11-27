package ${packagePath}.dto;

import ${packagePath}.entity.${entityName};
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * ${comment!} DTO
 *
 * @author ${author}
 * @since ${date}
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ${entityName}DTO extends ${entityName} {

}
