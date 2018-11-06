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
 * @create 2017-08-31 16:35
 * @description 智联平台转化类
 */
@Data
public class ZhilianCustomerVo {
    @Excel(name = "姓名", isImportField = "realName")
    private String realName;

    private String recruitChannel;

    @Excel(name = "性别", isImportField = "sex", replace = {"男_1", "女_2"})
    private Integer sex;

    @Excel(name = "出生日期",isImportField = "age")
    private String age;
    @Excel(name = "工作年限", isImportField = "workAge")
    private Integer workAge;
    @Excel(name = "移动电话", isImportField = "tel")
    private String tel;
    @Excel(name = "户口", isImportField = "nativePlace")
    private String nativePlace;
    @Excel(name = "现在单位", isImportField = "workExperience")
    private String workExperience;
    @Excel(name = "学校名称", isImportField = "graduateFrom")
    private String graduateFrom;
    @Excel(name = "专业名称", isImportField = "majorIn")
    private String majorIn;
    @Excel(name = "最高学历", isImportField = "educationBg")
    private String educationBg;
    @Excel(name = "应聘职位", isImportField = "applyJob")
    private String applyJob;
    @Excel(name = "应聘公司")
    private String license;
    @Excel(name = "目前居住地", isImportField = "liveAddress")
    private String liveAddress;
    @Excel(name = "投递时间", isImportField = "sendTime", format = "yyyy-MM-dd")
    private Date sendTime;
    @Excel(name = "期望月薪(税前)", isImportField = "expectSalary")
    private String expectSalary;
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

    public ZhilianCustomerVo() {
    }



    /**
     * 将智联平台导入的ZhilianCustomerVo对象转换成CustomerInfo对象
     *
     * @param
     */
    public ImportInfo changeToCustomerInfo(ZhilianCustomerVo z) {
        ImportInfo i = new ImportInfo();
        BeanUtils.copyProperties(z,i);
        return i;
    }

    /**
     * 获取简历中的年龄
     * @param age
     * @return
     */
    private Integer modifyAge(String age){
        Integer result = null;
        if(age != null){
            Integer year = Integer.parseInt(age.substring(0, age.indexOf('/')));
            Date d = new Date();
            Integer now = Integer.parseInt(DateUtils.getYear(d));
            result = now - year;
        }
        return result;
    }

    @Override
    public String toString() {
        return "ZhilianCustomerVo{" +
                "realName='" + realName + '\'' +
                ", recruitChannel=" + recruitChannel +
                ", sex=" + sex +
                ", age='" + age + '\'' +
                ", workAge=" + workAge +
                ", tel='" + tel + '\'' +
                ", nativePlace='" + nativePlace + '\'' +
                ", workExperience='" + workExperience + '\'' +
                ", graduateFrom='" + graduateFrom + '\'' +
                ", majorIn='" + majorIn + '\'' +
                ", educationBg=" + educationBg +
                ", applyJob='" + applyJob + '\'' +
                ", license='" + license + '\'' +
                ", liveAddress='" + liveAddress + '\'' +
                ", sendTime=" + sendTime +
                ", expectSalary='" + expectSalary + '\'' +
                ", introducer='" + introducer + '\'' +
                ", companyId=" + companyId +
                '}';
    }
}
