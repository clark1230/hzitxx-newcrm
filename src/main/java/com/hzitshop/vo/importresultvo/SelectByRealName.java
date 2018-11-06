package com.hzitshop.vo.importresultvo;

public class SelectByRealName {
    private int customerId;
    private String introducer;
    private String name;
    private int importStatus;
    private int isDelete;
    private String tel;
    public SelectByRealName() {
    }

    public SelectByRealName(String introducer, String name, int importStatus, int isDelete) {
        this.introducer = introducer;
        this.name = name;
        this.importStatus = importStatus;
        this.isDelete = isDelete;
    }

    public SelectByRealName(int customerId, String introducer, String name, int importStatus, int isDelete, String tel) {
        this.customerId = customerId;
        this.introducer = introducer;
        this.name = name;
        this.importStatus = importStatus;
        this.isDelete = isDelete;
        this.tel = tel;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getIntroducer() {
        return introducer;
    }

    public void setIntroducer(String introducer) {
        this.introducer = introducer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImportStatus() {
        return importStatus;
    }

    public void setImportStatus(int importStatus) {
        this.importStatus = importStatus;
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
        return "SelectByRealName{" +
                "introducer='" + introducer + '\'' +
                ", name='" + name + '\'' +
                ", importStatus=" + importStatus +
                ", isDelete=" + isDelete +
                '}';
    }
}
