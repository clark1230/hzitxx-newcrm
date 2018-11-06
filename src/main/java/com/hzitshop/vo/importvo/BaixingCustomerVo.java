package com.hzitshop.vo.importvo;

import com.hzitshop.entity.ImportInfo;
import com.hzitshop.util.DateUtils;
import com.mchange.v2.beans.BeansUtils;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author 吕游
 * @company 合众艾特
 * @create 2017-09-21 14:07
 * @description
 */
@Data
public class BaixingCustomerVo {
    @Excel(name = "姓名", isImportField = "realName")
    private String realName;

    private String recruitChannel; //应聘渠道
    @Excel(name = "性别", isImportField = "sex", replace = {"男_1", "女_2"})
    private Integer sex;
    @Excel(name = "年龄", isImportField = "true_age",suffix = "岁")
    private Integer age;
    @Excel(name = "投递职位")
    private String applyJob;
    @Excel(name = "联系方式", isImportField = "tel")
    private String tel;
    @Excel(name = "学历", isImportField = "educationBg")
    private String educationBg;
    @Excel(name = "工作年限", isImportField = "workAge")
    private String workAge;
    @Excel(name = "工作经历", isImportField = "workExperience")
    private String workExperience;
    @Excel(name = "教育经历", isImportField = "educateExperience")
    private String educateExperience;
    @Excel(name = "投递时间", isImportField = "sendTime")
    private String sendTime;
    @Excel(name = "目前居住地", isImportField = "liveAddress")
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

    public BaixingCustomerVo() {
    }



    private Integer modifyWorkAge(String workAge){
        Integer result = null;
        if(workAge!=null) {
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public ImportInfo changeToCustomerInfo(BaixingCustomerVo bx){
        ImportInfo i = new ImportInfo();
        /*i.setRealName(bx.getRealName());
        i.setRecruitChannel(bx.getRecruitChannel());
        i.setSex(bx.getSex());
        i.setAge(bx.getAge());
        i.setApplyJob(bx.getApplyJob());
        i.setTel(bx.getTel());
        i.setEducationBg(bx.getEducationBg());
        Integer workAge = this.modifyWorkAge(bx.getWorkAge());
        i.setWorkAge(workAge);
        i.setWorkExperience(bx.getWorkExperience());
        i.setEducateExperience(bx.getEducateExperience());
        i.setIntroducer(bx.getIntroducer());
        i.setCompanyId(bx.getCompanyId());
        i.setCreateTime(new Date());
        i.setLiveAddress(bx.getLiveAddress());
        i.setSendTime(DateUtils.parse(bx.getSendTime(),"yyyy年MM月dd日"));
        i.setLicense(bx.getLicense());
        i.setSource(bx.getSource());*/
        BeanUtils.copyProperties(bx,i);
        return i;
    }

    @Override
    public String
    toString() {
        return "BaixingCustomerVo{" +
                "realName='" + realName + '\'' +
                ", recruitChannel=" + recruitChannel +
                ", sex=" + sex +
                ", age=" + age +
                ", applyJob='" + applyJob + '\'' +
                ", tel='" + tel + '\'' +
                ", educationBg=" + educationBg +
                ", workAge='" + workAge + '\'' +
                ", workExperience='" + workExperience + '\'' +
                ", educateExperience='" + educateExperience + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", liveAddress='" + liveAddress + '\'' +
                ", license='" + license + '\'' +
                ", introducer='" + introducer + '\'' +
                ", companyId=" + companyId +
                '}';
    }
}
