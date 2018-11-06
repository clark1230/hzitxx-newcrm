package com.hzitshop.vo.huixiao;

import lombok.Data;

import java.util.Date;

/**
 * 会销导出模板
 */
@Data
public class ExportHuiXaioVo {
    private Date createTime;//日期
    private String realName;//姓名
    private String gender;//性别
    private String tel;//电话号码
    private String education;//学历
    private String majorIn;//专业
    private String job;//应聘岗位
    private String tryLearn;//视同
    private String signUp;//报名
    private String think;//意向
    private String interviewer;//面试官

    @Override
    public String
    toString() {
        return "ExportHuiXaioVo{" +
                "createTime=" + createTime +
                ", realName='" + realName + '\'' +
                ", gender='" + gender + '\'' +
                ", tel='" + tel + '\'' +
                ", education='" + education + '\'' +
                ", majorIn='" + majorIn + '\'' +
                ", job='" + job + '\'' +
                ", tryLearn='" + tryLearn + '\'' +
                ", signUp='" + signUp + '\'' +
                ", think='" + think + '\'' +
                ", interviewer='" + interviewer + '\'' +
                '}';
    }
}
