package com.hzitshop.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author 冼耀基
 * @since 2017-02-18
 */
@TableName("customer_info")
@Data
public class CustomerInfo extends Model<CustomerInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 客户id
     */
	@TableId(value="customer_id",type= IdType.AUTO)
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
	private Integer workAge;
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
	@TableField("target_skill")
	private String targetSkill;
    /**
     * 推荐人（创量部电话邀约）
     */
	private String introducer;
    /**
     * 备注
     */
	private String memo;
    /**
     * 最后修改时间 格式2015-10-09
     */
	@TableField("last_time")
	private Date lastTime;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
	/**
	 * 公司编号
	 */
	@TableField("company_id")
	private Integer companyId;
	/**
	 * 是否删除
	 */
	private Integer isDelete;
    /**
     * 关单人
     */
	private Integer guandan;
    /**
     * 是否是会销(0:是,1:不是)
     */
	private String isMarket;
	/**
	 * 设置跟进时间
	 */
	@TableField("trace_time")
	private Date traceTime;
	/**
	 * 会销备注
	 */
	@TableField("market_memo")
	private String  marketMemo;
	/**
	 * 试听备注
	 */
	@TableField("learn_memo")
	private String  learnMemo;
	/**
	 * 是否试听
	 */
	@TableField("isLearn")
	private String isLearn;
	/**
	 * 0:属于咨询师的数据 1:属于运营的数据，默认为0
	 */
	@TableField("customer_type")
	private Integer customerType;
	/**
	 * 运营人员
	 */
	@TableField("yunying")
	private Integer yunying;
	/**
	 * 最近跟进时间
	 */
	@TableField("record_time")
	private Date recordTime;

	@Override
	protected Serializable pkVal() {
		return this.customerId;
	}

	@Override
	public String toString() {
		return "CustomerInfo{" +
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
				", targetSkill='" + targetSkill + '\'' +
				", introducer='" + introducer + '\'' +
				", memo='" + memo + '\'' +
				", lastTime=" + lastTime +
				", createTime=" + createTime +
				", companyId=" + companyId +
				", isDelete=" + isDelete +
				", guandan=" + guandan +
				", isMarket='" + isMarket + '\'' +
				'}';
	}
}
