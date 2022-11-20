package com.github.mengweijin.vitality.demo.excel;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * @author mengweijin
 * @date 2022/11/20
 */
public class UserStatusConverter implements Converter<Integer> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Integer convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
                                     GlobalConfiguration globalConfiguration) {
        return "删除".equals(cellData.getStringValue()) ? 1 : 0;
    }

    @Override
    public WriteCellData<?> convertToExcelData(Integer value, ExcelContentProperty contentProperty,
                                               GlobalConfiguration globalConfiguration) {
        return new WriteCellData<>(1 == value ? "删除" : "正常");
    }

}
