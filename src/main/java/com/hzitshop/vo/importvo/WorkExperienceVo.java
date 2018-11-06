package com.hzitshop.vo.importvo;

import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelTarget;

import java.io.Serializable;

/**
 * @author 吕游
 * @company 合众艾特
 * @create 2017-09-04 14:28
 * @description 中华英才简历模板中的工作经历实体
 */
@Data
public class WorkExperienceVo implements Serializable{
    @Excel(name = "职位名称", needMerge = true)
    private String job;
    @Excel(name = "起止时间（xxxx年x月-xxxx年月）",needMerge = true)
    private String strTime;
    @Excel(name = "公司名称", needMerge = true)
    private String workExperience;

    public WorkExperienceVo() {
    }

    @Override
    public String toString() {
        return "WorkExperienceVo{" +
                "job='" + job + '\'' +
                ", strTime='" + strTime + '\'' +
                ", workExperience='" + workExperience + '\'' +
                '}';
    }
}
