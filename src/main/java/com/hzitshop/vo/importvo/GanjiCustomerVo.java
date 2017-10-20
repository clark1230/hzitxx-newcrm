package com.hzitshop.vo.importvo;

import com.hzitshop.entity.ImportInfo;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.util.Date;

/**
 * @author 吕游
 * @company 合众艾特
 * @create 2017-09-04 11:19
 * @description 赶集网平台转换类
 */
public class GanjiCustomerVo {
    @Excel(name = "姓名", isImportField = "realName")
    private String realName;
    private Integer recruitChannel;
    @Excel(name = "性别", isImportField = "sex",replace = {"男_1", "女_2"})
    private Integer sex;
    @Excel(name = "年龄", isImportField = "age")
    private Integer age;
    @Excel(name = "学历", isImportField = "educationBg",replace = {"小学_8","初中_9","初中及以下_9","中专/技校_10","高中_11","大专_12","本科_13","硕士_14","硕士及以上_14","博士_15"})
    private Integer educationBg;
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
    @Excel(name = "应聘公司", isImportField = "license")
    private String license;
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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
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

    public String getWorkAge() {
        return workAge;
    }

    public void setWorkAge(String workAge) {
        this.workAge = workAge;
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

    public String getLiveAddress() {
        return liveAddress;
    }

    public void setLiveAddress(String liveAddress) {
        this.liveAddress = liveAddress;
    }

    public String getExpectSalary() {
        return expectSalary;
    }

    public void setExpectSalary(String expectSalary) {
        this.expectSalary = expectSalary;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
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
        i.setRealName(gj.getRealName());
        i.setRecruitChannel(gj.getRecruitChannel());
        i.setSex(gj.getSex());
        i.setAge(gj.getAge());
        i.setEducationBg(gj.getEducationBg());
        Integer workAge = this.modifyWorkAge(gj.getWorkAge());
        i.setWorkAge(workAge);
        i.setTel(gj.getTel());
        i.setApplyJob(gj.getApplyJob());
        i.setIntroducer(gj.getIntroducer());
        i.setCompanyId(gj.getCompanyId());
        i.setCreateTime(new Date());
        i.setLiveAddress(gj.getLiveAddress());
        i.setSendTime(gj.getSendTime());
        i.setExpectSalary(gj.getExpectSalary());
        i.setLicense(gj.getLicense());
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
