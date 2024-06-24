package com.swen90014.paplatypusbackend.controller.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChartUtil {
    private int Completed;
    private int NotYetCompleted;
    private int OverDue;
}
