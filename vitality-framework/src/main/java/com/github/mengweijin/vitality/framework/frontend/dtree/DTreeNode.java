package com.github.mengweijin.vitality.framework.frontend.dtree;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mengweijin
 * @date 2022/11/27
 */
@Data
public class DTreeNode {

    private String id;

    private String title;

    private Boolean last;

    private String parentId;

    private List<DTreeNode> children = new ArrayList<>();

}
