package com.hzitshop.vo.front;

import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.util.Date;

/**
 * 前台导出实体类
 */
@Data
public class FrontVo {

    private int customerId;
    @Excel(name="上门时间",format="yyyy-MM-dd")
    private Date createTime;//上门时间
    @Excel(name="姓名")
    private String realName;//姓名
    @Excel(name= "联系方式")
    private String tel;
    @Excel(name="性别")
    private String gender;//性别
    @Excel(name="学历")
    private String education;//学历
    @Excel(name="年龄")
    private int age;//年龄
    @Excel(name="应聘渠道")
    private String recruitChannel;//应聘渠道
    @Excel(name="应聘职位")
    private String job;//应聘职位
    @Excel(name="邀约人")
    private String introducer;//邀约人
    @Excel(name="咨询顾问")
    private String interviewer;//咨询顾问
    @Excel(name= "会销讲师")
    private String market;//会销

    @Override
    public String toString() {
        return "FrontVo{" +
                "createTime='" + createTime + '\'' +
                ", realName='" + realName + '\'' +
                ", gender='" + gender + '\'' +
                ", education='" + education + '\'' +
                ", age=" + age +
                ", recruitChannel='" + recruitChannel + '\'' +
                ", job='" + job + '\'' +
                ", introducer='" + introducer + '\'' +
                ", interviewer='" + interviewer + '\'' +
                ", market='" + market + '\'' +
                '}';
    }
}
