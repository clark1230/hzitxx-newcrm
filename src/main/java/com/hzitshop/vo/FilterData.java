package com.hzitshop.vo;

public class FilterData {
    private int customerId;
    private String realName;
    private String tel; //电话号码
    private int isDelete;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "FilterData{" +
                "customerId=" + customerId +
                ", realName='" + realName + '\'' +
                ", tel='" + tel + '\'' +
                ", isDelete=" + isDelete +
                '}';
    }
}
