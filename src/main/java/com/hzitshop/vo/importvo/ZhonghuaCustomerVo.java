package com.hzitshop.vo.importvo;

import com.hzitshop.entity.ImportInfo;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import org.jeecgframework.poi.excel.annotation.ExcelTarget;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 吕游
 * @company 合众艾特
 * @create 2017-09-04 11:47
 * @description 中华英才网转换类
 */
@ExcelTarget("ZhonghuaCustomerVo")
public class ZhonghuaCustomerVo implements Serializable {
    @Excel(name = "姓名", isImportField = "true_realName",needMerge = true)
    private String realName;
    @Excel(name = "性别", isImportField = "true_sex",replace = {"男_1", "女_2"},needMerge = true)
    private Integer sex;
    @Excel(name = "年龄（岁）", isImportField = "true_age",suffix = "岁",needMerge = true)
    private Integer age;
    private Integer recruitChannel;
    @Excel(name = "工作年限", isImportField = "true_workAge",needMerge = true)
    private String workAge;
    @Excel(name = "手机", isImportField = "true_tel",needMerge = true)
    private String tel;
    @Excel(name = "应聘（适合）职位",needMerge = true)
    private String applyJob;
    /**
     * 推荐人（创量部电话邀约）
     */
    private String introducer;
    /**
     * 所属校区ID
     */
    private Integer companyId;
    @ExcelCollection(name = "最高学历")
    private List<EduBackgroundVo> eduBackgroundVo;
    @ExcelCollection(name = "最近工作经历")
    private List<WorkExperienceVo> workExperienceVo;

    public ZhonghuaCustomerVo() {
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

    public Integer getRecruitChannel() {
        return recruitChannel;
    }

    public void setRecruitChannel(Integer recruitChannel) {
        this.recruitChannel = recruitChannel;
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

    public List<EduBackgroundVo> getEduBackgroundVo() {
        return eduBackgroundVo;
    }

    public void setEduBackgroundVo(List<EduBackgroundVo> eduBackgroundVo) {
        this.eduBackgroundVo = eduBackgroundVo;
    }

    public List<WorkExperienceVo> getWorkExperienceVo() {
        return workExperienceVo;
    }

    public void setWorkExperienceVo(List<WorkExperienceVo> workExperienceVo) {
        this.workExperienceVo = workExperienceVo;
    }

    public String getApplyJob() {
        return applyJob;
    }

    public void setApplyJob(String applyJob) {
        this.applyJob = applyJob;
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

    public ImportInfo changeToCustomerInfo(ZhonghuaCustomerVo zh){
        ImportInfo i = new ImportInfo();
        i.setRealName(zh.getRealName());
        i.setSex(zh.getSex());
        i.setAge(zh.getAge());
        i.setRecruitChannel(zh.getRecruitChannel());
        Integer workAge = this.modifyWorkAge(zh.getWorkAge());
        i.setWorkAge(workAge);
        i.setTel(zh.getTel());
        i.setIntroducer(zh.getIntroducer());
        i.setCompanyId(zh.getCompanyId());
        if(zh.getEduBackgroundVo() != null && zh.getEduBackgroundVo().get(0) != null){
            i.setEducationBg(zh.getEduBackgroundVo().get(0).getEducationBg());
            i.setGraduateFrom(zh.getEduBackgroundVo().get(0).getGraduateFrom());
            i.setGraduateTime(zh.getEduBackgroundVo().get(0).getGraduateTime());
            i.setMajorIn(zh.getEduBackgroundVo().get(0).getMajorIn());
        }
        if(zh.getWorkExperienceVo() != null && zh.getWorkExperienceVo().get(0) != null){
            i.setJob(zh.getWorkExperienceVo().get(0).getJob());
            i.setWorkExperience(zh.getWorkExperienceVo().get(0).getWorkExperience());
        }
        i.setApplyJob(zh.getApplyJob());
        i.setCreateTime(new Date());
        return i;
    }

    @Override
    public String toString() {
        return "ZhonghuaCustomerVo{" +
                "realName='" + realName + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", recruitChannel=" + recruitChannel +
                ", workAge='" + workAge + '\'' +
                ", tel='" + tel + '\'' +
                ", applyJob='" + applyJob + '\'' +
                ", introducer='" + introducer + '\'' +
                ", companyId=" + companyId +
                ", eduBackgroundVo=" + eduBackgroundVo +
                ", workExperienceVo=" + workExperienceVo +
                '}';
    }
}
