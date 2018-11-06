package com.hzitshop.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.util.Date;


/**
 * <p>
 * 
 * </p>
 *
 * @author 冼耀基
 * @since 2017-02-18
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ImportInfoVo {

    private static final long serialVersionUID = 1L;

    /**
     * 客户id
     */
	private Integer customerId;
    /**
     * 客户姓名
     */
	@Excel(name="学员姓名")
	private String realName;

	//@Excel(name="咨询师")
	private String userIdMsg;
	/**
	 * 创建时间
	 */
	@Excel(name="录入时间",format="yyyy-MM-dd")
	private Date createTime;
    /**
     * 性别[1-男  2-女]
     */
	private Integer sex;

	@Excel(name="性别")
	private String sexMsg;
    /**
     * 年龄
     */
    @Excel(name="年龄")
	private Integer age;
    /**
     * 籍贯
     */
    @Excel(name="籍贯")
	private String nativePlace;
    /**
     * 联系电话
     */
    @Excel(name="电话号码")
	private String tel;
    /**
     * 微信账号
     */
    @Excel(name="微信号")
	private String wechatNo;
    /**
     * 扣扣号
     */
    @Excel(name="qq号")
	private String qq;
    /**
     * 学历[1-小学 2-初中 3-高中 4-大专 5-本科 6-研究生 0-其他]
     */
    @Excel(name="学历")
	private String educationBg;

	//@Excel(name="学历")
	private String educationBgMsg;
    /**
     * 毕业时间
     */
    @Excel(name="毕业时间")
	private String graduateTime;
    /**
     * 毕业院校
     */
    @Excel(name="毕业学校")
	private String graduateFrom;
    /**
     * 专业
     */
    @Excel(name="专业")
	private String majorIn;
    /**
     * 工作年限
     */
    @Excel(name="工作年限")
	private String workAge;
    /**
     * 工作经历
     */
    @Excel(name="工作经历")
	private String workExperience;
    /**
     * 从事职业
     */
    @Excel(name="从事职业")
	private String job;
    /**
     * 教育培训经历
     */
    @Excel(name="教育培训经历")
	private String educateExperience;
    /**
     * 应聘渠道[1-智联 2-前程无忧 3-58同城 4-转介绍 5-中华英才 6-其他待定]
     */
	@Excel(name="应聘渠道")
	private String recruitChannel;

	private String recruitChannelMsg;
    /**
     * 客户状态[待定]已报名，已就业，意向[],无效
     */
	private Integer customerState;

	//@Excel(name="客户状态")
	private String customerStateMsg;
    /**
     * 客户级别[待定]（ABCD）
     */
	private String customerLevel;
	//@Excel(name="客户级别")
	private String customerLevelMsg;
    /**
     * 咨询师id
     */
	private Integer userId;

    /**
     * 应聘职位
     */
    @Excel(name="应聘职位")
	private String applyJob;
    /**
     * 推荐人（创量部电话邀约）
     */

	private String introducer;

	@Excel(name="邀约人")
	private String introducerMsg;
    /**
     * 备注
     */
    @Excel(name="备注")
	private String memo;
    /**
     * 最后跟进时间 格式2015-10-09
     */
   // @Excel(name="最后跟进时间",format="yyyy-MM-dd")
	private Date lastTime;

    
	private Integer companyId;
	//@Excel(name="所属校区")
	private String companyIdMsg;
	
	private Integer isDelete;
    /**
     * 关单人
     */
	private Integer guandan;
//	@Excel(name="关单人")
	private String guandaMsg;
    /**
     * 是否是会销(0:是,1:不是)
     */
	//private String isMarket;
	/**
	 * 导入的简历类型(1:投递的简历,2:下载的简历)
	 */
	private Integer cvType;

	@Excel(name = "简历类型")
	private String cvTypeMsg;
	/**
	 * 目前居住地
	 */
	private String liveAddress;

	/**
	 * 目前年收入
	 */
	private String curIncome;

	/**
	 * 应聘公司
	 */
	private String license;

	/**
	 * 期望薪资
	 */
	private String expectSalary;

	private String jobStatus;

	private Date sendTime;

	/**
	 * 录入人
	 */
	@Excel(name="录入人")
	private String createBy;

//	@Excel(name="录入时间",format="yyyy-MM-dd")
//	private Date createTime;

	/**
	 * 数据是否重复
	 * 数据是否存在重复,0:不重复,其他重复
	 */
	private int repeatNum;
	/**
	 * 投递来源
	 */
	@Excel(name="投递来源")
	private String source;

	/**
	 * 应聘者的状态 0:不做任何操作 1:预约 2.回访,默认0
	 */
	@TableField("import_status")
	private int importStatus;

	@TableField("color")
	private String color;

	/**
	 * 状态
	 */
	@TableField("status")
	private String status;

	@Override
	public String toString() {
		return "ImportInfoVo{" +
				"customerId=" + customerId +
				", realName='" + realName + '\'' +
				", userIdMsg='" + userIdMsg + '\'' +
				", createTime=" + createTime +
				", sex=" + sex +
				", sexMsg='" + sexMsg + '\'' +
				", age=" + age +
				", nativePlace='" + nativePlace + '\'' +
				", tel='" + tel + '\'' +
				", wechatNo='" + wechatNo + '\'' +
				", qq='" + qq + '\'' +
				", educationBg=" + educationBg +
				", educationBgMsg='" + educationBgMsg + '\'' +
				", graduateTime='" + graduateTime + '\'' +
				", graduateFrom='" + graduateFrom + '\'' +
				", majorIn='" + majorIn + '\'' +
				", workAge=" + workAge +
				", workExperience='" + workExperience + '\'' +
				", job='" + job + '\'' +
				", educateExperience='" + educateExperience + '\'' +
				", recruitChannel=" + recruitChannel +
				", recruitChannelMsg='" + recruitChannelMsg + '\'' +
				", customerState=" + customerState +
				", customerStateMsg='" + customerStateMsg + '\'' +
				", customerLevel='" + customerLevel + '\'' +
				", customerLevelMsg='" + customerLevelMsg + '\'' +
				", userId=" + userId +
				", applyJob='" + applyJob + '\'' +
				", introducer='" + introducer + '\'' +
				", introducerMsg='" + introducerMsg + '\'' +
				", memo='" + memo + '\'' +
				", lastTime=" + lastTime +
				", companyId=" + companyId +
				", companyIdMsg='" + companyIdMsg + '\'' +
				", isDelete=" + isDelete +
				", guandan=" + guandan +
				", guandaMsg='" + guandaMsg + '\'' +
				//", isMarket='" + isMarket + '\'' +
				", cvType=" + cvType +
				", cvTypeMsg='" + cvTypeMsg + '\'' +
				", liveAddress='" + liveAddress + '\'' +
				", curIncome='" + curIncome + '\'' +
				", license='" + license + '\'' +
				", expectSalary='" + expectSalary + '\'' +
				", jobStatus='" + jobStatus + '\'' +
				", sendTime=" + sendTime +
				", createBy='" + createBy + '\'' +
				", repeatNum=" + repeatNum +
				'}';
	}
}
