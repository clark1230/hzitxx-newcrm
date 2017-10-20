package com.hzitshop.vo.importvo;

import com.hzitshop.entity.ImportInfo;
import com.hzitshop.util.DateUtils;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.util.Date;

/**
 * @author 吕游
 * @company 合众艾特
 * @create 2017-09-21 14:07
 * @description
 */
public class BaixingCustomerVo {
    @Excel(name = "姓名", isImportField = "realName")
    private String realName;
    private Integer recruitChannel;
    @Excel(name = "性别", isImportField = "sex", replace = {"男_1", "女_2"})
    private Integer sex;
    @Excel(name = "年龄", isImportField = "true_age",suffix = "岁")
    private Integer age;
    @Excel(name = "投递职位")
    private String applyJob;
    @Excel(name = "联系方式", isImportField = "tel")
    private String tel;
    @Excel(name = "学历", isImportField = "educationBg",replace = {"小学_8","初中及以下_9","初中_9","中专/技校_10","高中_11","大专_12","本科_13","硕士_14","硕士及以上_14","博士_15"})
    private Integer educationBg;
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getIntroducer() {
        return introducer;
    }

    public void setIntroducer(String introducer) {
        this.introducer = introducer;
    }

    public String getEducateExperience() {
        return educateExperience;
    }

    public void setEducateExperience(String educateExperience) {
        this.educateExperience = educateExperience;
    }

    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }

    public String getWorkAge() {
        return workAge;
    }

    public void setWorkAge(String workAge) {
        this.workAge = workAge;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getEducationBg() {
        return educationBg;
    }

    public void setEducationBg(Integer educationBg) {
        this.educationBg = educationBg;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getApplyJob() {
        return applyJob;
    }

    public void setApplyJob(String applyJob) {
        this.applyJob = applyJob;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getRecruitChannel() {
        return recruitChannel;
    }

    public void setRecruitChannel(Integer recruitChannel) {
        this.recruitChannel = recruitChannel;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getLiveAddress() {
        return liveAddress;
    }

    public void setLiveAddress(String liveAddress) {
        this.liveAddress = liveAddress;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
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

    public ImportInfo changeToCustomerInfo(BaixingCustomerVo bx){
        ImportInfo i = new ImportInfo();
        i.setRealName(bx.getRealName());
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
