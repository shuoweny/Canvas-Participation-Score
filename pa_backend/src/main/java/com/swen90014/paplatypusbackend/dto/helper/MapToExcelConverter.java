package com.swen90014.paplatypusbackend.dto.helper;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.util.Map;

public class MapToExcelConverter implements Converter<Map> {

    @Override
    public Class supportJavaTypeKey() {
        return Map.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }



    @Override
    public WriteCellData<?> convertToExcelData(Map value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {

        return new WriteCellData<>(value.toString());
    }
}