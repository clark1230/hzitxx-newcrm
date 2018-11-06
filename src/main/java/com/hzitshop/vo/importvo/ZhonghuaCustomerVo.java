package com.hzitshop.vo.importvo;

import com.hzitshop.entity.ImportInfo;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import org.jeecgframework.poi.excel.annotation.ExcelTarget;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 吕游
 * @company 合众艾特
 * @create 2017-09-04 11:47
 * @description 中华英才网转换类
 */
@Data
@ExcelTarget("ZhonghuaCustomerVo")
public class ZhonghuaCustomerVo implements Serializable {
    @Excel(name = "姓名", isImportField = "realName",needMerge = true)
    private String realName;
    @Excel(name = "性别", isImportField = "sex",replace = {"男_1", "女_2"},needMerge = true)
    private Integer sex;
    @Excel(name = "年龄（岁）", isImportField = "age",suffix = "岁",needMerge = true)
    private Integer age;
    //应聘渠道
    private String recruitChannel;

    @Excel(name = "工作年限", isImportField = "workAge",needMerge = true)
    private String workAge;
    @Excel(name = "手机", isImportField = "tel",needMerge = true)
    private String tel;
    @Excel(name = "应聘（适合）职位",isImportField = "applyJob",needMerge = true)
    private String applyJob;
    @Excel(name = "目前居住地（城市-区）", isImportField = "liveAddress",needMerge = true)
    private String liveAddress;
    @Excel(name = "应聘公司",isImportField = "license",needMerge = true)
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

//    @Excel(name="最高学历",isImportField = "educationBg")
//    private String educationBg;
//    @Excel(name = "最近工作经历",isImportField = "workExperience")
//    private String workExperience;
    //@Excel(name = "求职意向",isImportField = "applyJob")
   // private String applyJob;
    @ExcelCollection(name = "最高学历")
    private List<EduBackgroundVo> eduBackgroundVo;
    @ExcelCollection(name = "最近工作经历")
    private List<WorkExperienceVo> workExperienceVo;
    @ExcelCollection(name = "求职意向")
    private List<ApplyJobVo> applyJobVo;

    public ZhonghuaCustomerVo() {
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

        BeanUtils.copyProperties(zh,i);
        if( zh.getEduBackgroundVo()!= null && zh.getEduBackgroundVo().size() >0){
            for(EduBackgroundVo ev: zh.getEduBackgroundVo()){
                i.setEducationBg(ev.getEducationBg());
                i.setGraduateFrom(ev.getGraduateFrom());
                i.setMajorIn(ev.getMajorIn());
                i.setGraduateTime(ev.getGraduateTime());
            }
        }
        if(zh.getApplyJobVo()!= null && zh.getApplyJobVo().size() >0){
            for(ApplyJobVo av: zh.getApplyJobVo()){
                i.setExpectSalary(av.getExpectSalary());
            }
        }
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
                ", liveAddress='" + liveAddress + '\'' +
                ", license='" + license + '\'' +
                ", introducer='" + introducer + '\'' +
                ", companyId=" + companyId +
                '}';
    }
}
