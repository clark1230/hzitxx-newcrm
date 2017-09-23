package com.hzitshop.vo.importvo;

import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @author 吕游
 * @company 合众艾特
 * @create 2017-09-23 17:04
 * @description
 */
public class ApplyJobVo implements Serializable{
    @Excel(name = "期望地点")
    private String place;
    @Excel(name = "期望工作")
    private String hopeJob;
    @Excel(name = "期望薪资")
    private String expectSalary;

    public ApplyJobVo() {
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getHopeJob() {
        return hopeJob;
    }

    public void setHopeJob(String hopeJob) {
        this.hopeJob = hopeJob;
    }

    public String getExpectSalary() {
        return expectSalary;
    }

    public void setExpectSalary(String expectSalary) {
        this.expectSalary = expectSalary;
    }

    @Override
    public String toString() {
        return "ApplyJobVo{" +
                "place='" + place + '\'' +
                ", hopeJob='" + hopeJob + '\'' +
                ", expectSalary='" + expectSalary + '\'' +
                '}';
    }
}
