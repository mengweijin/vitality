package com.github.mengweijin.vitality.framework.frontend.dtree;

import lombok.Data;

import java.util.List;

/**
 * @author mengweijin
 * @date 2022/11/27
 */
@Data
public class DTreeLayuiStyleDTO {

    private List<DTreeNode> data;

    private Integer code = 0;

    private String msg = "SUCCESS";

    public DTreeLayuiStyleDTO(List<DTreeNode> data) {
        this.data = data;
    }

    public DTreeLayuiStyleDTO(Integer code, String msg, List<DTreeNode> data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

}
