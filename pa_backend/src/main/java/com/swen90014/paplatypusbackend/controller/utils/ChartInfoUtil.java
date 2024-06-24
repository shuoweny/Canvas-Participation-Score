package com.swen90014.paplatypusbackend.controller.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChartInfoUtil {
    private String diagramName;
    private Object diagramData;
}
