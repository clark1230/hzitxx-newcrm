package com.hzitshop.vo;

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
	@Excel(name="咨询师")
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
	private Integer educationBg;
	@Excel(name="学历")
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
    @Excel(name="工作年龄")
	private Integer workAge;
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
	private Integer recruitChannel;
	@Excel(name="应聘渠道")
	private String recruitChannelMsg;
    /**
     * 客户状态[待定]已报名，已就业，意向[],无效
     */
	private Integer customerState;
	@Excel(name="客户状态")
	private String customerStateMsg;
    /**
     * 客户级别[待定]（ABCD）
     */
	private String customerLevel;
	@Excel(name="客户级别")
	private String customerLevelMsg;
    /**
     * 咨询师id
     */
	private Integer userId;

    /**
     * 客户感兴趣的目标技能
     */
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
    @Excel(name="最后跟进时间",format="yyyy-MM-dd")
	private Date lastTime;

    
	private Integer companyId;
	@Excel(name="所属校区")
	private String companyIdMsg;
	
	private Integer isDelete;
    /**
     * 关单人
     */
	private Integer guandan;
	@Excel(name="关单人")
	private String guandaMsg;
    /**
     * 是否是会销(0:是,1:不是)
     */
	private String isMarket;
	/**
	 * 导入的简历类型(1:投递的简历,2:下载的简历)
	 */
	private Integer cvType;
	private String cvTypeMsg;


	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
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

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getWechatNo() {
		return wechatNo;
	}

	public void setWechatNo(String wechatNo) {
		this.wechatNo = wechatNo;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public Integer getEducationBg() {
		return educationBg;
	}

	public void setEducationBg(Integer educationBg) {
		this.educationBg = educationBg;
	}

	public String getGraduateTime() {
		return graduateTime;
	}

	public void setGraduateTime(String graduateTime) {
		this.graduateTime = graduateTime;
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

	public Integer getWorkAge() {
		return workAge;
	}

	public void setWorkAge(Integer workAge) {
		this.workAge = workAge;
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

	public String getEducateExperience() {
		return educateExperience;
	}

	public void setEducateExperience(String educateExperience) {
		this.educateExperience = educateExperience;
	}

	public Integer getRecruitChannel() {
		return recruitChannel;
	}

	public void setRecruitChannel(Integer recruitChannel) {
		this.recruitChannel = recruitChannel;
	}

	public Integer getCustomerState() {
		return customerState;
	}

	public void setCustomerState(Integer customerState) {
		this.customerState = customerState;
	}

	public String getCustomerLevel() {
		return customerLevel;
	}

	public void setCustomerLevel(String customerLevel) {
		this.customerLevel = customerLevel;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getGuandan() {
		return guandan;
	}

	public void setGuandan(Integer guandan) {
		this.guandan = guandan;
	}

	public String getIsMarket() {
		return isMarket;
	}

	public void setIsMarket(String isMarket) {
		this.isMarket = isMarket;
	}

	public String getEducationBgMsg() {
		return educationBgMsg;
	}

	public void setEducationBgMsg(String educationBgMsg) {
		this.educationBgMsg = educationBgMsg;
	}

	public String getGuandaMsg() {
		return guandaMsg;
	}

	public void setGuandaMsg(String guandaMsg) {
		this.guandaMsg = guandaMsg;
	}

	public String getCustomerStateMsg() {
		return customerStateMsg;
	}

	public void setCustomerStateMsg(String customerStateMsg) {
		this.customerStateMsg = customerStateMsg;
	}

	public String getCustomerLevelMsg() {
		return customerLevelMsg;
	}

	public void setCustomerLevelMsg(String customerLevelMsg) {
		this.customerLevelMsg = customerLevelMsg;
	}

	public String getUserIdMsg() {
		return userIdMsg;
	}

	public void setUserIdMsg(String userIdMsg) {
		this.userIdMsg = userIdMsg;
	}

	public String getRecruitChannelMsg() {
		return recruitChannelMsg;
	}

	public void setRecruitChannelMsg(String recruitChannelMsg) {
		this.recruitChannelMsg = recruitChannelMsg;
	}

	public String getIntroducerMsg() {
		return introducerMsg;
	}

	public void setIntroducerMsg(String introducerMsg) {
		this.introducerMsg = introducerMsg;
	}

	public String getCompanyIdMsg() {
		return companyIdMsg;
	}

	public void setCompanyIdMsg(String companyIdMsg) {
		this.companyIdMsg = companyIdMsg;
	}

	public String getSexMsg() {
		return sexMsg;
	}

	public void setSexMsg(String sexMsg) {
		this.sexMsg =sexMsg;
	}

	public Integer getCvType() {
		return cvType;
	}

	public void setCvType(Integer cvType) {
		this.cvType = cvType;
	}

	public String getCvTypeMsg() {
		return cvTypeMsg;
	}

	public void setCvTypeMsg(String cvTypeMsg) {
		this.cvTypeMsg = cvTypeMsg;
	}

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
				", isMarket='" + isMarket + '\'' +
				", cvType=" + cvType +
				", cvTypeMsg='" + cvTypeMsg + '\'' +
				'}';
	}
}
