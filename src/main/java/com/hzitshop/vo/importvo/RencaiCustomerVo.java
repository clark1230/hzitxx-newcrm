package com.hzitshop.vo.importvo;

import com.hzitshop.entity.CustomerInfo;
import com.hzitshop.entity.ImportInfo;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author 吕游
 * @company 合众艾特
 * @create 2017-09-04 11:34
 * @description 人才热线平台转换类
 */
@Data
public class RencaiCustomerVo {
    /**
     * 使用isHyperLink = true 来解决人才热线导入数据，用户名是超链接时，导入失败的bug
     */
    @Excel(name = "姓名", isImportField = "realName")
    private String realName;

    private String recruitChannel;
    @Excel(name = "性别", isImportField = "sex",replace = {"男_1", "女_2"})
    private Integer sex;
    @Excel(name = "年龄", isImportField = "age")
    private Integer age;
    @Excel(name = "手机", isImportField = "tel")
    private String tel;
    @Excel(name = "学历", isImportField = "educationBg")
    private String educationBg;
    @Excel(name = "学校", isImportField = "graduateFrom")
    private String graduateFrom;
    @Excel(name = "专业", isImportField = "majorIn")
    private String majorIn;
    @Excel(name = "最近公司", isImportField = "workExperience")
    private String workExperience;
    @Excel(name = "最近职位", isImportField = "job")
    private String job;
    @Excel(name = "应聘职位")
    private String applyJob;
    @Excel(name = "求职状态", isImportField = "jobStatus")
    private String jobStatus;

    @Excel(name = "月薪", isImportField = "curIncome")
    private String curIncome;
    @Excel(name = "所在地", isImportField = "liveAddress")
    private String liveAddress;
    @Excel(name = "接收/下载日期", isImportField = "sendTime", format = "yyyy-MM-dd")
    private Date sendTime;
    @Excel(name = "应聘公司")
    private String license;
    @Excel(name="后台名称",isImportField = "source")
    private String source;

    /**
     * 推荐人（创量部电话邀约）
     */
    private String introducer;
    /**
     * 所属校区ID
     */
    private Integer companyId;

    public RencaiCustomerVo() {
    }

    public ImportInfo changeToCustomerInfo(RencaiCustomerVo rc){
        ImportInfo i = new ImportInfo();
        BeanUtils.copyProperties(rc,i);
        return i;
    }


    @Override
    public String
    toString() {
        return "RencaiCustomerVo{" +
                "realName='" + realName + '\'' +
                ", recruitChannel=" + recruitChannel +
                ", sex=" + sex +
                ", age=" + age +
                ", tel='" + tel + '\'' +
                ", educationBg=" + educationBg +
                ", graduateFrom='" + graduateFrom + '\'' +
                ", majorIn='" + majorIn + '\'' +
                ", workExperience='" + workExperience + '\'' +
                ", job='" + job + '\'' +
                ", applyJob='" + applyJob + '\'' +
                ", jobStatus='" + jobStatus + '\'' +
                ", curIncome='" + curIncome + '\'' +
                ", liveAddress='" + liveAddress + '\'' +
                ", sendTime=" + sendTime +
                ", introducer='" + introducer + '\'' +
                ", companyId=" + companyId +
                '}';
    }
}
