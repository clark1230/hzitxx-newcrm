package com.hzitshop.vo.importvo;

import com.hzitshop.entity.ImportInfo;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author 吕游
 * @company 合众艾特
 * @create 2017-09-04 11:19
 * @description 赶集网平台转换类
 */
@Data
public class GanjiCustomerVo {
    @Excel(name = "姓名", isImportField = "realName")
    private String realName;

    private String recruitChannel;//招聘渠道
    @Excel(name = "性别", isImportField = "sex",replace = {"男_1", "女_2"})
    private Integer sex;
    @Excel(name = "年龄", isImportField = "age")
    private Integer age;
    @Excel(name = "学历", isImportField = "educationBg")
    private String educationBg;
    @Excel(name = "工作年限", isImportField = "workAge")
    private String workAge;
    @Excel(name = "手机", isImportField = "tel")
    private String tel;
    @Excel(name = "求职职位")
    private String applyJob;
    @Excel(name = "投递/下载时间", isImportField = "sendTime", format = "yyyy-MM-dd")
    private Date sendTime;
    @Excel(name = "期望薪资", isImportField = "expectSalary")
    private String expectSalary;
    @Excel(name = "期望工作地点", isImportField = "liveAddress")
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

    public GanjiCustomerVo() {
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

    public ImportInfo changeToCustomerInfo(GanjiCustomerVo gj){
        ImportInfo i = new ImportInfo();
        BeanUtils.copyProperties(gj,i);
        return i;
    }

    @Override
    public String toString() {
        return "GanjiCustomerVo{" +
                "realName='" + realName + '\'' +
                ", recruitChannel=" + recruitChannel +
                ", sex=" + sex +
                ", age=" + age +
                ", educationBg=" + educationBg +
                ", workAge='" + workAge + '\'' +
                ", tel='" + tel + '\'' +
                ", applyJob='" + applyJob + '\'' +
                ", sendTime=" + sendTime +
                ", expectSalary='" + expectSalary + '\'' +
                ", liveAddress='" + liveAddress + '\'' +
                ", license='" + license + '\'' +
                ", introducer='" + introducer + '\'' +
                ", companyId=" + companyId +
                '}';
    }
}
