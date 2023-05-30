package com.github.mengweijin.vitality.system.dto;

import com.github.mengweijin.vitality.system.entity.VtlMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.util.List;

/**
 * @author mengweijin
 * @date 2023/5/20
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class VtlMenuTreeDataDTO extends VtlMenu {

    private String href;

    private List<VtlMenuTreeDataDTO> children;

}
