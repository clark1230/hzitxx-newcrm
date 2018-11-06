package com.hzitshop.vo.importvo;


import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelTarget;

import java.io.Serializable;

/**
 * @author 吕游
 * @company 合众艾特
 * @create 2017-09-04 14:04
 * @description 中华英才简历模板中的学历实体
 */
@Data
public class EduBackgroundVo implements Serializable{
    @Excel(name = "最高学历",needMerge = true)
    private String educationBg;
    @Excel(name = "起止时间（xxxx年x月-xxxx年月）",needMerge = true)
    private String graduateTime;
    @Excel(name = "学校",needMerge = true)
    private String graduateFrom;
    @Excel(name = "专业",needMerge = true)
    private String majorIn;

    public EduBackgroundVo() {
    }

    @Override
    public String toString() {
        return "EduBackgroundVo{" +
                "educationBg=" + educationBg +
                ", graduateTime='" + graduateTime + '\'' +
                ", graduateFrom='" + graduateFrom + '\'' +
                ", majorIn='" + majorIn + '\'' +
                '}';
    }
}
