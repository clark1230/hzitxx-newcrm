package com.hzitshop.vo.importvo;

import com.hzitshop.entity.ImportInfo;
import com.hzitshop.util.DateUtils;
import org.jeecgframework.poi.excel.annotation.Excel;


import java.util.Date;


/**
 * @author 吕游
 * @company 合众艾特
 * @create 2017-08-31 16:23
 * @description 51Job平台转换类
 */
public class QianchengCustomerVo {
    @Excel(name = "姓名", isImportField = "realName")
    private String realName;
    private Integer recruitChannel;
    @Excel(name = "性别", isImportField = "sex",replace = {"男_1", "女_2"})
    private Integer sex;
    @Excel(name = "出生日期",isImportField = "age")
    private String age;
    @Excel(name = "户口/国籍", isImportField = "nativePlace")
    private String nativePlace;
    @Excel(name = "工作年限", isImportField = "workAge")
    private String workAge;
    @Excel(name = "学历/学位", isImportField = "educationBg",replace = {"小学_8","初中及以下_9","初中_9","中专_10","中技_10","高中_11","大专_12","本科_13","硕士_14","博士_15","MBA_15"})
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
    @Excel(name = "应聘公司", isImportField = "license")
    private String license;
    @Excel(name = "应聘日期", isImportField = "sendTime", format = "yyyy-MM-dd")
    private Date sendTime;
    @Excel(name = "期望薪资", isImportField = "expectSalary")
    private String expectSalary;
    @Excel(name = "求职状态", isImportField = "jobStatus")
    private String jobStatus;
    @Excel(name = "目前年收入", isImportField = "curIncome")
    private String curIncome;
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

    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMajorIn() {
        return majorIn;
    }

    public void setMajorIn(String majorIn) {
        this.majorIn = majorIn;
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

    public String getWorkAge() {
        return workAge;
    }

    public void setWorkAge(String workAge) {
        this.workAge = workAge;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Integer getRecruitChannel() {
        return recruitChannel;
    }

    public void setRecruitChannel(Integer recruitChannel) {
        this.recruitChannel = recruitChannel;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
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

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getExpectSalary() {
        return expectSalary;
    }

    public void setExpectSalary(String expectSalary) {
        this.expectSalary = expectSalary;
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
        i.setRealName(qc.getRealName());
        i.setRecruitChannel(qc.getRecruitChannel());
        i.setSex(qc.getSex());
        i.setAge(qc.modifyAge(qc.getAge()));
        i.setNativePlace(qc.getNativePlace());
        Integer workAge = this.modifyWorkAge(qc.getWorkAge());
        i.setWorkAge(workAge);
        i.setEducationBg(qc.getEducationBg());
        i.setGraduateFrom(qc.getGraduateFrom());
        i.setMajorIn(qc.getMajorIn());
        i.setTel(qc.getTel());
        i.setJob(qc.getJob());
        i.setWorkExperience(qc.getWorkExperience());
        i.setApplyJob(qc.getApplyJob());
        i.setIntroducer(qc.getIntroducer());
        i.setCompanyId(qc.getCompanyId());
        i.setCreateTime(new Date());
        i.setLiveAddress(qc.getLiveAddress());
        i.setExpectSalary(qc.getExpectSalary());
        i.setSendTime(qc.getSendTime());
        i.setLicense(qc.getLicense());
        i.setCurIncome(qc.getCurIncome());
        i.setJobStatus(qc.getJobStatus());
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
