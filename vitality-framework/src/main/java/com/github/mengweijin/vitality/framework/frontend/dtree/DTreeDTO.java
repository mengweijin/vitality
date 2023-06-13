package com.github.mengweijin.vitality.framework.frontend.dtree;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mengweijin
 * @date 2022/11/27
 */
@Data
public class DTreeDTO {

    private DTreeStatus status = new DTreeStatus();

    private List<DTreeNode> data = new ArrayList<>();

}
