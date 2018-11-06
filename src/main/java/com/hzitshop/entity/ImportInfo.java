package com.hzitshop.entity;

import com.baomidou.mybatisplus.activerecord.Model;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;


/**
 * <p>
 * <p>
 * </p>
 *
 * @author 冼耀基
 * @since 2017-02-18
 */
@TableName(value = "import_info")
@Data
public class ImportInfo extends Model<ImportInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 客户id
     */
    @TableId(value = "customer_id", type = IdType.AUTO)
    private Integer customerId;
    /**
     * 客户姓名
     */
    @TableField("real_name")
    private String realName;
    /**
     * 性别[1-男  2-女]
     */
    private Integer sex;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 籍贯
     */
    @TableField("native_place")
    private String nativePlace;
    /**
     * 联系电话
     */
    private String tel;
    /**
     * 微信账号
     */
    @TableField("wechat_no")
    private String wechatNo;
    /**
     * 扣扣号
     */
    private String qq;
    /**
     * 学历[1-小学 2-初中 3-高中 4-大专 5-本科 6-研究生 0-其他]
     */
    @TableField("education_bg")
    private String educationBg;
    /**
     * 毕业时间
     */
    @TableField("graduate_time")
    private String graduateTime;
    /**
     * 毕业院校
     */
    @TableField("graduate_from")
    private String graduateFrom;
    /**
     * 专业
     */
    @TableField("major_in")
    private String majorIn;
    /**
     * 工作年限
     */
    @TableField("work_age")
    private String workAge;
    /**
     * 工作经历
     */
    @TableField("work_experience")
    private String workExperience;
    /**
     * 从事职业
     */
    private String job;
    /**
     * 教育培训经历
     */
    @TableField("educate_experience")
    private String educateExperience;
    /**
     * 应聘渠道[1-智联 2-前程无忧 3-58同城 4-转介绍 5-中华英才 6-其他待定]
     */
    @TableField("recruit_channel")
    private String recruitChannel;
    /**
     * 客户状态[待定]已报名，已就业，意向[],无效
     */
    @TableField("customer_state")
    private Integer customerState;
    /**
     * 客户级别[待定]（ABCD）
     */
    @TableField("customer_level")
    private String customerLevel;
    /**
     * 咨询师id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 客户感兴趣的目标技能
     */
    @TableField("apply_job")
    private String applyJob;
    /**
     * 推荐人（创量部电话邀约）
     */
    private String introducer;
    /**
     * 备注
     */
    private String memo;
    /**
     * 最后跟进时间 格式2015-10-09
     */
    @TableField("last_time")
    private Date lastTime;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    @TableField("company_id")
    private Integer companyId;

    private Integer isDelete;

    /**
     * 目前居住地
     */
    @TableField("live_address")
    private String liveAddress;

    /**
     * 目前年收入
     */
    @TableField("cur_income")
    private String curIncome;

    /**
     * 应聘公司
     */
    private String license;

    /**
     * 期望薪资
     */
    @TableField("expect_salary")
    private String expectSalary;

    @TableField("job_status")
    private String jobStatus;

    @TableField("send_time")
    private Date sendTime;

    /**
     * 关单人
     */
    private Integer guandan;
    /**
     * 是否是会销(0:是,1:不是)
     */
    private String isMarket;
    /**
     * 导入的简历类型(1:投递的简历,2:下载的简历)
     */
    private Integer cvType;
    /**
     * 录入人
     */
    private String createBy;
    /**
     * 数据是否重复
     * 数据是否存在重复,0:不重复,其他重复
     */
    private Integer repeatNum;
    /**
     * 投递来源
     */
    @TableField("source")
    private String source;
    /**
     * 应聘者的状态 0:不做任何操作 1:预约 2.回访,默认0
     */
    @TableField("import_status")
    private Integer importStatus;
    /**
     * 颜色
     */
    @TableField("color")
    private String color;
    /**
     * 状态
     */
    @TableField("status")
    private String status;
    @Override
    public String toString() {
        return "ImportInfo{" +
                "customerId=" + customerId +
                ", realName='" + realName + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", nativePlace='" + nativePlace + '\'' +
                ", tel='" + tel + '\'' +
                ", wechatNo='" + wechatNo + '\'' +
                ", qq='" + qq + '\'' +
                ", educationBg=" + educationBg +
                ", graduateTime='" + graduateTime + '\'' +
                ", graduateFrom='" + graduateFrom + '\'' +
                ", majorIn='" + majorIn + '\'' +
                ", workAge=" + workAge +
                ", workExperience='" + workExperience + '\'' +
                ", job='" + job + '\'' +
                ", educateExperience='" + educateExperience + '\'' +
                ", recruitChannel=" + recruitChannel +
                ", customerState=" + customerState +
                ", customerLevel='" + customerLevel + '\'' +
                ", userId=" + userId +
                ", applyJob='" + applyJob + '\'' +
                ", introducer='" + introducer + '\'' +
                ", memo='" + memo + '\'' +
                ", lastTime=" + lastTime +
                ", createTime=" + createTime +
                ", companyId=" + companyId +
                ", isDelete=" + isDelete +
                ", liveAddress='" + liveAddress + '\'' +
                ", curIncome='" + curIncome + '\'' +
                ", license='" + license + '\'' +
                ", expectSalary='" + expectSalary + '\'' +
                ", jobStatus='" + jobStatus + '\'' +
                ", sendTime=" + sendTime +
                ", guandan=" + guandan +
                ", isMarket='" + isMarket + '\'' +
                ", cvType=" + cvType +
                ", createBy='" + createBy + '\'' +
                ", repeatNum=" + repeatNum +
                '}';
    }

    @Override
    protected Serializable pkVal() {
        return this.customerId;
    }
}
