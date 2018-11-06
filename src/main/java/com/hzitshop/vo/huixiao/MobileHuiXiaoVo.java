package com.hzitshop.vo.huixiao;

import lombok.Data;

/**
 * 手机端会销数据封装类
 */
@Data
public class MobileHuiXiaoVo {
    private int customerId;//编号
    private String realName;//学员名称
    private String gender;//性别
    private String tel;//手机号码
    private String isMarket;//是否进入会销


    @Override
    public String toString() {
        return "MobileHuiXiaoVo{" +
                "realName='" + realName + '\'' +
                ", gender='" + gender + '\'' +
                ", tel='" + tel + '\'' +
                ", isMarket='" + isMarket + '\'' +
                '}';
    }
}
