package com.hzitshop.vo.importresultvo;


import java.util.Date;

public class ImportVoNameId {
    private int customerId; //应聘者编号
    private String realName;//应聘者姓名

    private int importStatus;//应聘者状态
    private Date createTime;

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

    public int getImportStatus() {
        return importStatus;
    }

    public void setImportStatus(int importStatus) {
        this.importStatus = importStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ImportVoNameId{" +
                "customerId=" + customerId +
                ", realName='" + realName + '\'' +
                '}';
    }
}
