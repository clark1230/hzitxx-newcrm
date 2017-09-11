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
        i.setWorkAge(z.getWorkAge());
        i.setTel(z.getTel());
        i.setNativePlace(z.getNativePlace());
        i.setWorkExperience(z.getWorkExperience());
        i.setGraduateFrom(z.getGraduateFrom());
        i.setMajorIn(z.getMajorIn());
        i.setEducationBg(z.getEducationBg());
        i.setIntroducer(z.getIntroducer());
        i.setCompanyId(z.getCompanyId());
        i.setCreateTime(new Date());
        return i;
    }
    @Override
    public String toString() {
        return "ZhilianCustomerVo{" +
                "realName='" + realName + '\'' +
                ", recruitChannel=" + recruitChannel +
                ", sex=" + sex +
                ", workAge=" + workAge +
                ", tel='" + tel + '\'' +
                ", nativePlace='" + nativePlace + '\'' +
                ", workExperience='" + workExperience + '\'' +
                ", graduateFrom='" + graduateFrom + '\'' +
                ", majorIn='" + majorIn + '\'' +
                ", educationBg=" + educationBg +
                ", introducer='" + introducer + '\'' +
                ", companyId=" + companyId +
                '}';
    }
}
