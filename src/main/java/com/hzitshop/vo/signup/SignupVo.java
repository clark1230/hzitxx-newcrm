package com.hzitshop.vo.signup;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SignupVo {
    /**
     * 编号
     */
    private int id;
    /**
     * 学员
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
     * 录入时间
     */
    private Date createTime;
    /**
     * 备注
     */
    private String memo;

    /**
     * 状态(已报名/退款/退学)
     */
    private int status;
    /**
     * 押金
     */
    private BigDecimal deposit;
    /**
     * 所属公司
     */
    private String company;

    /**
     * 到账时间
     */
    private Date accountingDate;

    /**
     *报名缴费时间
     */
    private Date  registrationAndPaymentTime;
    /**
     * 关单人
     */
    private String guandan;

    @Override
    public String toString() {
        return "SignupVo{" +
                "id=" + id +
                ", realName='" + realName + '\'' +
                ", sex=" + sex +
                ", tel='" + tel + '\'' +
                ", createTime=" + createTime +
                ", memo='" + memo + '\'' +
                '}';
    }
}
