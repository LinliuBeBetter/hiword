package com.liulin.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "cadi_pdca_left_kpi_target")
public class CadiPdcaLeftKpiTarget {
    @ExcelProperty("指标")
    private String label;

    @ExcelProperty("月度目标值")
    private String current_month_target_value;

    @ExcelProperty("week1")
    @NumberFormat
    private String week1_target_value;

    @ExcelProperty("week2")
    @NumberFormat
    private String week2_target_value;

    @ExcelProperty("week3")
    @NumberFormat
    private String week3_target_value;

    @ExcelProperty("week4")
    @NumberFormat
    private String week4_target_value;

    @ExcelProperty("week5")
    @NumberFormat
    private String week5_target_value;

    @ExcelProperty("当周")
    @NumberFormat
    private String current_week_actual;

    @ExcelProperty("当月")
    @NumberFormat
    private String monthly_cumulative_actual;

    //    上传月周
    @ExcelIgnore
    private String week;

    @ExcelIgnore
    private String series_or_major_issue;

    @Override
    public String toString() {
        return "CadiPdcaLeftKpiTarget{" +
                "label='" + label + '\'' +
                ", current_month_target_value='" + current_month_target_value + '\'' +
                ", week1_target_value='" + week1_target_value + '\'' +
                ", week2_target_value='" + week2_target_value + '\'' +
                ", week3_target_value='" + week3_target_value + '\'' +
                ", week4_target_value='" + week4_target_value + '\'' +
                ", week5_target_value='" + week5_target_value + '\'' +
                ", week='" + week + '\'' +
                ", series_or_major_issue='" + series_or_major_issue + '\'' +
                '}';
    }
}