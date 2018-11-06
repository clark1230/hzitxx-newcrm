package com.hzitshop.vo.importvo;

import com.hzitshop.entity.ImportInfo;
import com.hzitshop.util.DateUtils;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.beans.BeanUtils;


import java.util.Date;


/**
 * @author 吕游
 * @company 合众艾特
 * @create 2017-08-31 16:23
 * @description 51Job平台转换类
 */
@Data
public class QianchengCustomerVo {
    @Excel(name = "姓名", isImportField = "realName")
    private String realName;
    private String recruitChannel;
    @Excel(name = "性别", isImportField = "sex",replace = {"男_1", "女_2"})
    private Integer sex;
    @Excel(name = "出生日期",isImportField = "age")
    private String age;
    @Excel(name = "户口/国籍", isImportField = "nativePlace")
    private String nativePlace;

    @Excel(name = "工作年限", isImportField = "workAge")
    private String workAge;

//    @Excel(name = "学历/学位", isImportField = "educationBg")
    private Integer educationBg;

    @Excel(name = "毕业学校", isImportField = "graduateFrom")
    private String graduateFrom;
    @Excel(name = "专业", isImportField = "majorIn")
    private String majorIn;
    @Excel(name = "联系电话", isImportField = "tel")
    private String tel;
    @Excel(name = "最近一个职位", isImportField = "job")
    private String job;
    @Excel(name = "最近一家公司", isImportField = "workExperience")
    private String workExperience;
    @Excel(name = "应聘职位")
    private String applyJob;
    @Excel(name = "目前居住地", isImportField = "liveAddress")
    private String liveAddress;
    @Excel(name = "应聘公司")
    private String license;
    @Excel(name = "应聘日期", isImportField = "sendTime", format = "yyyy-MM-dd")
    private Date sendTime;
    @Excel(name = "期望薪资", isImportField = "expectSalary")
    private String expectSalary;
    @Excel(name = "求职状态", isImportField = "jobStatus")
    private String jobStatus;
    @Excel(name = "目前年收入", isImportField = "curIncome")
    private String curIncome;

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
    public QianchengCustomerVo() {
    }


    /**
     * 工作年限格式化
     * @param workAge
     * @return
     */
    private Integer modifyWorkAge(String workAge) {
        Integer result = null;
        if(workAge != null) {
            if (workAge.contains("年")) {
                result = Integer.parseInt(workAge.substring(0, workAge.indexOf("年")));
            } else {
                result = 0;
            }
        }else {
            return null;
        }
        return result;
    }

    /**
     * 获取简历中的年龄
     * @param age
     * @return
     */
    private Integer modifyAge(String age){
        Integer result = null;
        if(age != null){
            Integer year = Integer.parseInt(age.substring(0, age.indexOf('-')));
            Date d = new Date();
            Integer now = Integer.parseInt(DateUtils.getYear(d));
            result = now - year;
        }
        return result;
    }

    /**
     * 将智联平台导入的ZhilianCustomerVo对象转换成CustomerInfo对象
     *
     * @param
     */
    public ImportInfo changeToCustomerInfo(QianchengCustomerVo qc) {
        ImportInfo i = new ImportInfo();
        BeanUtils.copyProperties(qc,i);
        return i;
    }

    @Override
    public String toString() {
        return "QianchengCustomerVo{" +
                "realName='" + realName + '\'' +
                ", recruitChannel=" + recruitChannel +
                ", sex=" + sex +
                ", age='" + age + '\'' +
                ", nativePlace='" + nativePlace + '\'' +
                ", workAge='" + workAge + '\'' +
                ", educationBg=" + educationBg +
                ", graduateFrom='" + graduateFrom + '\'' +
                ", majorIn='" + majorIn + '\'' +
                ", tel='" + tel + '\'' +
                ", job='" + job + '\'' +
                ", workExperience='" + workExperience + '\'' +
                ", applyJob='" + applyJob + '\'' +
                ", liveAddress='" + liveAddress + '\'' +
                ", license='" + license + '\'' +
                ", sendTime=" + sendTime +
                ", expectSalary='" + expectSalary + '\'' +
                ", jobStatus='" + jobStatus + '\'' +
                ", curIncome='" + curIncome + '\'' +
                ", introducer='" + introducer + '\'' +
                ", companyId=" + companyId +
                '}';
    }
}
