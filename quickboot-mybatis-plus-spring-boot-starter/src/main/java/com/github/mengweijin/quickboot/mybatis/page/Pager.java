package com.github.mengweijin.quickboot.mybatis.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.List;

/**
 * @author Meng Wei Jin
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Component
public class Pager<T> extends Page<T> {

    private static final long serialVersionUID = -5428777368936766478L;

    /**
     * 查询数据列表table返回对象
     */
    private List<T> dataList = Collections.emptyList();

    /**
     * 手动分页
     */
    public static <E> IPage<E> handPage(IPage<E> page, List<E> list){
        if(list == null){
            list = Collections.EMPTY_LIST;
        } else {
            page.setTotal(list.size());
        }

        int current = (int)page.getCurrent();
        int size = (int)page.getSize();

        // 截取的开始位置
        int startIndex = (current - 1) * size;
        // 截取的结束位置
        int endIndex = current * size;
        endIndex = list.size() > endIndex ? endIndex : (list.size() % size + startIndex);

        list = list.subList(startIndex, endIndex);
        page.setRecords(list);
        return page;
    }
}
