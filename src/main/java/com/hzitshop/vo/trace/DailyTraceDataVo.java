package com.hzitshop.vo.trace;

import lombok.Data;

/**
 * 每日跟进数据封装类
 */
@Data
public class DailyTraceDataVo {
    private Integer customerId;//客户编号
    private String realName;//客户名称
    private String tel;//客户电话号码
    private int userId;//用户编号
    private String name;//用户名称

    @Override
    public String toString() {
        return "DailyTraceDataVo{" +
                "customerId=" + customerId +
                ", realName='" + realName + '\'' +
                ", tel='" + tel + '\'' +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                '}';
    }
}
