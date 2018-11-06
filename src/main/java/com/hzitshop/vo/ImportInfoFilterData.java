package com.hzitshop.vo;

import java.util.List;

/**
 * 数据导入筛选
 */
public class ImportInfoFilterData {
    private  String createBy;//录入人
    private List<FilterData> data;
    private int total;//每日总数据
    private int effectiveNum; //有效数据

    public ImportInfoFilterData() {
    }

    public ImportInfoFilterData(String createBy, List<FilterData> data) {
        this.createBy = createBy;
        this.data = data;
    }

    public ImportInfoFilterData(String createBy, List<FilterData> data, int total, int effectiveNum) {
        this.createBy = createBy;
        this.data = data;
        this.total = total;
        this.effectiveNum = effectiveNum;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public List<FilterData> getData() {
        return data;
    }

    public void setData(List<FilterData> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getEffectiveNum() {
        return effectiveNum;
    }

    public void setEffectiveNum(int effectiveNum) {
        this.effectiveNum = effectiveNum;
    }

    @Override
    public String toString() {
        return "ImportInfoFilterData{" +
                "createBy='" + createBy + '\'' +
                ", data=" + data +
                ", totaol=" + total +
                ", effectiveNum=" + effectiveNum +
                '}';
    }
}
