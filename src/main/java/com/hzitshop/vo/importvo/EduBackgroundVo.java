package com.hzitshop.vo.importvo;


import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelTarget;

import java.io.Serializable;

/**
 * @author 吕游
 * @company 合众艾特
 * @create 2017-09-04 14:04
 * @description 中华英才简历模板中的学历实体
 */
public class EduBackgroundVo implements Serializable{
    @Excel(name = "最高学历",needMerge = true,replace = {"小学_8","初中_9","中专_10","其他_10","高中_11","大专_12","本科_13","硕士_14","博士_15"})
    private Integer educationBg;
    @Excel(name = "起止时间（xxxx年x月-xxxx年月）",needMerge = true)
    private String graduateTime;
    @Excel(name = "学校",needMerge = true)
    private String graduateFrom;
    @Excel(name = "专业",needMerge = true)
    private String majorIn;

    public EduBackgroundVo() {
    }

    public Integer getEducationBg() {
        return educationBg;
    }

    public void setEducationBg(Integer educationBg) {
        this.educationBg = educationBg;
    }

    public String getMajorIn() {
        return majorIn;
    }

    public void setMajorIn(String majorIn) {
        this.majorIn = majorIn;
    }

    public String getGraduateFrom() {
        return graduateFrom;
    }

    public void setGraduateFrom(String graduateFrom) {
        this.graduateFrom = graduateFrom;
    }

    public String getGraduateTime() {
        return graduateTime;
    }

    public void setGraduateTime(String graduateTime) {
        this.graduateTime = graduateTime;
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
