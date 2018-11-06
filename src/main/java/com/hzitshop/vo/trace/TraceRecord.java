package com.hzitshop.vo.trace;

import lombok.Data;

import java.util.Date;
@Data
public class TraceRecord {

    private int recordId;//编号

    private int customerId;//客户编号

    private String empName;//咨询师
    /**
     * 客户名称
     */
    private String name;
    /**
     * 跟进消息
     */
    private String content;
    /**
     * 跟进日期
     */
    private Date recordDate;


    public TraceRecord() {
    }



}
