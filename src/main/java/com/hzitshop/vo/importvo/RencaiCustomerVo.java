package com.hzitshop.vo.importvo;

import com.hzitshop.entity.CustomerInfo;
import com.hzitshop.entity.ImportInfo;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.util.Date;

/**
 * @author 吕游
 * @company 合众艾特
 * @create 2017-09-04 11:34
 * @description 人才热线平台转换类
 */
public class RencaiCustomerVo {
    @Excel(name = "姓名", isImportField = "realName")
    private String realName;
    private Integer recruitChannel;
    @Excel(name = "性别", isImportField = "sex",replace = {"男_1", "女_2"})
    private Integer sex;
    @Excel(name = "年龄", isImportField = "age")
    private Integer age;
    @Excel(name = "手机", isImportField = "tel")
    private String tel;
    @Excel(name = "学历", isImportField = "educationBg",replace = {"小学_8","初中_9","中技_10","中专_10","高中_11","大专_12","本科_13","硕士_14","博士_15"})
    private Integer educationBg;
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getMajorIn() {
        return majorIn;
    }

    public void setMajorIn(String majorIn) {
        this.majorIn = majorIn;
    }

    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }

    public String getGraduateFrom() {
        return graduateFrom;
    }

    public void setGraduateFrom(String graduateFrom) {
        this.graduateFrom = graduateFrom;
    }

    public Integer getEducationBg() {
        return educationBg;
    }

    public void setEducationBg(Integer educationBg) {
        this.educationBg = educationBg;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIntroducer() {
        return introducer;
    }

    public void setIntroducer(String introducer) {
        this.introducer = introducer;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getApplyJob() {
        return applyJob;
    }

    public void setApplyJob(String applyJob) {
        this.applyJob = applyJob;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getCurIncome() {
        return curIncome;
    }

    public void setCurIncome(String curIncome) {
        this.curIncome = curIncome;
    }

    public String getLiveAddress() {
        return liveAddress;
    }

    public void setLiveAddress(String liveAddress) {
        this.liveAddress = liveAddress;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public ImportInfo changeToCustomerInfo(RencaiCustomerVo rc){
        ImportInfo i = new ImportInfo();
        i.setRealName(rc.getRealName());
        i.setRecruitChannel(rc.getRecruitChannel());
        i.setSex(rc.getSex());
        i.setAge(rc.getAge());
        i.setTel(rc.getTel());
        i.setEducationBg(rc.getEducationBg());
        i.setGraduateFrom(rc.getGraduateFrom());
        i.setMajorIn(rc.getMajorIn());
        i.setWorkExperience(rc.getWorkExperience());
        i.setJob(rc.getJob());
        i.setApplyJob(rc.getApplyJob());
        i.setIntroducer(rc.getIntroducer());
        i.setCompanyId(rc.getCompanyId());
        i.setCreateTime(new Date());
        i.setLiveAddress(rc.getLiveAddress());
        i.setJobStatus(rc.getJobStatus());
        i.setCurIncome(Integer.parseInt(rc.getCurIncome())*12+"");
        i.setSendTime(rc.getSendTime());
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
