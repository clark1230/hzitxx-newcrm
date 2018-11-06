package com.hzitshop.vo.customerinfovo;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 未跟进数据封装类
 */
public class NotFollowUpVo {
    /**
     * 学员id
     */
    private Integer customerId;
    /**
     * 学员名
     */
    private String realName;
    /**
     * 性别
     */
    private String sex;
    /**
     * 电话号码
     */
    private String tel;
    /**
     * 咨询师
     */
    private String name;
    /**
     * 公司
     */
    private  String company;
    /**
     * 备注
     */
    private String memo;
    /**
     * 上门时间
     */
    private Date createTime;
    /**
     * 过去的天数
     */
    private String days;


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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
//
//    public Integer getDays() {
//        Date date = new Date();
//        Date createTime = this.getCreateTime();
//        SimpleDateFormat sdt = new SimpleDateFormat("D");
//        return (Integer.parseInt(sdt.format(date)) -Integer.parseInt(sdt.format(createTime)));
//    }
}
