package com.swen90014.paplatypusbackend.controller.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultUtil {
    private int state;
    private Object data;
    private String message;
}
