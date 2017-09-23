package com.hzitshop.vo.importvo;

import com.hzitshop.entity.ImportInfo;
import com.hzitshop.util.DateUtils;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.util.Date;

/**
 * @author 吕游
 * @company 合众艾特
 * @create 2017-08-31 16:35
 * @description 智联平台转化类
 */
public class ZhilianCustomerVo {
    @Excel(name = "姓名", isImportField = "realName")
    private String realName;
    private Integer recruitChannel;
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
    @Excel(name = "最高学历", isImportField = "educationBg",replace = {"小学_8","初中_9","中专_10","高中_11","大专_12","本科_13","硕士_14","博士_15"})
    private Integer educationBg;
    @Excel(name = "应聘职位", isImportField = "applyJob")
    private String applyJob;
    @Excel(name = "标签名称", isImportField = "license")
    private String license;
    @Excel(name = "目前居住地", isImportField = "liveAddress")
    private String liveAddress;
    @Excel(name = "投递(收藏)时间", isImportField = "sendTime", format = "yyyy-MM-dd")
    private Date sendTime;
    @Excel(name = "期望月薪(税前)", isImportField = "expectSalary")
    private String expectSalary;
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


    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getRecruitChannel() {
        return recruitChannel;
    }

    public void setRecruitChannel(Integer recruitChannel) {
        this.recruitChannel = recruitChannel;
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

    public Integer getWorkAge() {
        return workAge;
    }

    public void setWorkAge(Integer workAge) {
        this.workAge = workAge;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
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

    public String getMajorIn() {
        return majorIn;
    }

    public void setMajorIn(String majorIn) {
        this.majorIn = majorIn;
    }

    public Integer getEducationBg() {
        return educationBg;
    }

    public void setEducationBg(Integer educationBg) {
        this.educationBg = educationBg;
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

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
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

    public String getExpectSalary() {
        return expectSalary;
    }

    public void setExpectSalary(String expectSalary) {
        this.expectSalary = expectSalary;
    }

    /**
     * 将智联平台导入的ZhilianCustomerVo对象转换成CustomerInfo对象
     *
     * @param
     */
    public ImportInfo changeToCustomerInfo(ZhilianCustomerVo z) {
        ImportInfo i = new ImportInfo();
        i.setRealName(z.getRealName());
        i.setRecruitChannel(z.getRecruitChannel());
        i.setSex(z.getSex());
        i.setAge(z.modifyAge(z.getAge()));
        i.setWorkAge(z.getWorkAge());
        i.setTel(z.getTel());
        i.setNativePlace(z.getNativePlace());
        i.setWorkExperience(z.getWorkExperience());
        i.setGraduateFrom(z.getGraduateFrom());
        i.setMajorIn(z.getMajorIn());
        i.setEducationBg(z.getEducationBg());
        i.setIntroducer(z.getIntroducer());
        i.setCompanyId(z.getCompanyId());
        i.setApplyJob(z.getApplyJob());
        i.setCreateTime(new Date());
        i.setLiveAddress(z.getLiveAddress());
        i.setExpectSalary(z.getExpectSalary());
        i.setSendTime(z.getSendTime());
        i.setLicense(z.getLicense());
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
