package com.hzitshop.entity;

import lombok.Data;

import  java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author xianyaoji
 * @since 2018-03-12
 */
@Data
public class Signup implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 编号
     */
    private Integer id;
    /**
     * 学员编号
     */
    private Integer customerId;
    /**
     * 是否删除,0 不删除,1 删除 默认为 1
     */
    private String isDelete;
    /**
     * 报名时间
     */
    private Date createTime;
    /**
     * 备注信息
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
    private Integer companyId;

    /**
     * 到账时间
     */
    private Date accountingDate;

    /**
     *报名缴费时间
     */
    private Date  registrationAndPaymentTime;

    private String  guandan;

    @Override
    public String toString() {
        return "Signup{" +
        "id=" + id +
        ", customerId=" + customerId +
        ", isDelete=" + isDelete +
        ", createTime=" + createTime +
        ", memo=" + memo +
        "}";
    }
}