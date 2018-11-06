package com.hzitshop.vo.importvo;

import com.hzitshop.entity.CustomerInfo;
import com.hzitshop.entity.ImportInfo;
import com.hzitshop.util.DateUtils;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.beans.BeanUtils;

import java.text.DateFormat;
import java.util.Date;

/**
 * @author 吕游
 * @company 合众艾特
 * @create 2017-08-31 16:35
 * @description 58同城平台转换类
 */
@Data
public class TongchengCustomerVo {

    @Excel(name = "姓名", isImportField = "realName",isHyperlink = true)
    private String realName;
    private String recruitChannel;
    @Excel(name = "性别", isImportField = "sex", replace = {"男_1", "女_2"})
    private Integer sex;
    @Excel(name = "年龄",isImportField = "age")
    private Integer age;
    @Excel(name = "工作经验", isImportField = "workAge")
    private String workAge;
    @Excel(name = "通话密号/手机号", isImportField = "tel")
    private String tel;
    @Excel(name = "最高学历", isImportField = "educationBg")
    private String educationBg;
    @Excel(name = "学校名称", isImportField = "graduateFrom")
    private String graduateFrom;
    @Excel(name = "专业", isImportField = "majorIn")
    private String majorIn;
    @Excel(name = "现工作单位",isImportField = "workExperience")
    private String workExperience;
    @Excel(name = "现职位", isImportField = "job")
    private String job;
    @Excel(name = "应聘职位")
    private String applyJob;
    @Excel(name = "期望薪资", isImportField = "expectSalary")
    private String expectSalary;
    @Excel(name = "下载日期")
    private String sendTime;
    @Excel(name = "现居住地", isImportField = "liveAddress")
    private String liveAddress;
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

    public TongchengCustomerVo() {
    }


    public ImportInfo changeToCustomerInfo(TongchengCustomerVo tc){
        ImportInfo i = new ImportInfo();
        BeanUtils.copyProperties(tc,i);
        return i;
    }

    private Date modifySendTime(String sendTime){
        sendTime = sendTime.replace('/','-');
        Date result = DateUtils.parse(sendTime,"yyyy-MM-dd");
        return result;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    private Integer modifyWorkAge(String workAge){
        Integer result = null;
        if(workAge != null) {
            workAge = workAge.trim();
            if (workAge.contains("年")) {
                if (workAge.contains("-")) {
                    result = Integer.parseInt(workAge.substring(0, workAge.indexOf('-')));
                } else {
                    result = Integer.parseInt(workAge.substring(0, workAge.indexOf('年')));
                }
            } else {
                result = 0;
            }
        }else {
            return null;
        }
        return result;
    }

    @Override
    public String toString() {
        return "TongchengCustomerVo{" +
                "realName='" + realName + '\'' +
                ", recruitChannel=" + recruitChannel +
                ", sex=" + sex +
                ", age=" + age +
                ", workAge='" + workAge + '\'' +
                ", tel='" + tel + '\'' +
                ", educationBg=" + educationBg +
                ", graduateFrom='" + graduateFrom + '\'' +
                ", majorIn='" + majorIn + '\'' +
                ", workExperience='" + workExperience + '\'' +
                ", job='" + job + '\'' +
                ", applyJob='" + applyJob + '\'' +
                ", expectSalary='" + expectSalary + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", liveAddress='" + liveAddress + '\'' +
                ", license='" + license + '\'' +
                ", introducer='" + introducer + '\'' +
                ", companyId=" + companyId +
                '}';
    }
}
