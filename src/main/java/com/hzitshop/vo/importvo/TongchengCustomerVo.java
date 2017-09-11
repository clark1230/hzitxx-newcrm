package com.hzitshop.vo.importvo;

import com.hzitshop.entity.CustomerInfo;
import com.hzitshop.entity.ImportInfo;
import com.hzitshop.util.DateUtils;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.text.DateFormat;
import java.util.Date;

/**
 * @author 吕游
 * @company 合众艾特
 * @create 2017-08-31 16:35
 * @description 58同城平台转换类
 */
public class TongchengCustomerVo {
    @Excel(name = "姓名", isImportField = "realName")
    private String realName;
    private Integer recruitChannel;
    @Excel(name = "性别", isImportField = "sex", replace = {"男_1", "女_2"})
    private Integer sex;
    @Excel(name = "年龄",isImportField = "age")
    private Integer age;
    @Excel(name = "工作经验", isImportField = "workAge")
    private String workAge;
    @Excel(name = "手机", isImportField = "tel")
    private String tel;
    @Excel(name = "最高学历", isImportField = "educationBg",replace = {"小学_8","初中_9","中专/技校_10","高中_11","大专_12","本科_13","硕士_14","博士_15"})
    private Integer educationBg;
    @Excel(name = "学校名称", isImportField = "graduateFrom")
    private String graduateFrom;
    @Excel(name = "专业", isImportField = "majorIn")
    private String majorIn;
    @Excel(name = "现工作单位",isImportField = "workExperience")
    private String workExperience;
    @Excel(name = "现职位", isImportField = "job")
    private String job;
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

    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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

    public ImportInfo changeToCustomerInfo(TongchengCustomerVo tc){
        ImportInfo i = new ImportInfo();
        i.setRealName(tc.getRealName());
        i.setRecruitChannel(tc.getRecruitChannel());
        i.setSex(tc.getSex());
        i.setAge(tc.getAge());
        Integer workAge = this.modifyWorkAge(tc.getWorkAge());
        i.setWorkAge(workAge);
        i.setTel(tc.getTel());
        i.setEducationBg(tc.getEducationBg());
        i.setGraduateFrom(tc.getGraduateFrom());
        i.setMajorIn(tc.getMajorIn());
        i.setWorkExperience(tc.getWorkExperience());
        i.setJob(tc.getJob());
        i.setIntroducer(tc.getIntroducer());
        i.setCompanyId(tc.getCompanyId());
        i.setCreateTime(new Date());
        return i;
    }

    private Integer modifyWorkAge(String workAge){
        Integer result = null;
        workAge = workAge.trim();
        if(workAge.contains("年")){
            if(workAge.contains("-")){
                result = Integer.parseInt(workAge.substring(0,workAge.indexOf('-')));
            }
            result = Integer.parseInt(workAge.substring(0,workAge.indexOf('年')));
        }else {
            result = 0;
        }
        return result;
    }

    @Override
    public String toString() {
        return "TongchengCustomerVo{" +
                "realName='" + realName + '\'' +
                ", recruitChannel='" + recruitChannel + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", workAge='" + workAge + '\'' +
                ", tel='" + tel + '\'' +
                ", educationBg=" + educationBg +
                ", graduateFrom='" + graduateFrom + '\'' +
                ", majorIn='" + majorIn + '\'' +
                ", workExperience='" + workExperience + '\'' +
                ", job='" + job + '\'' +
                ", introducer='" + introducer + '\'' +
                ", companyId=" + companyId +
                '}';
    }
}